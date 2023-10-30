package com.hexagram2021.custom_worldgen.server;

import com.hexagram2021.custom_worldgen.common.utils.CWGLogger;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.NoiseRouterData;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public final class CWGCommands {
	private static final String POSITION = "pos";
	
	private CWGCommands() {
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("cwg")
				.requires(stack -> stack.hasPermission(2))
				.then(
						Commands.literal("noise")
								.then(
										Commands.argument(POSITION, BlockPosArgument.blockPos())
												.executes(context -> getWorldgenNoise(
														context.getSource().getPlayer(),
														context.getSource().getLevel(),
														BlockPosArgument.getBlockPos(context, POSITION)
												))
								)
				);
	}
	
	private static int getWorldgenNoise(@Nullable ServerPlayer player, ServerLevel level, BlockPos blockPos) {
		int x = QuartPos.fromBlock(blockPos.getX());
		int y = QuartPos.fromBlock(blockPos.getY());
		int z = QuartPos.fromBlock(blockPos.getZ());
		Climate.TargetPoint targetPoint = level.getChunkSource().randomState().sampler().sample(x, y, z);
		float c = Climate.unquantizeCoord(targetPoint.continentalness());
		float e = Climate.unquantizeCoord(targetPoint.erosion());
		float t = Climate.unquantizeCoord(targetPoint.temperature());
		float h = Climate.unquantizeCoord(targetPoint.humidity());
		float w = Climate.unquantizeCoord(targetPoint.weirdness());
		
		Consumer<String> printer;
		if(player == null) {
			printer = CWGLogger.LOGGER::info;
		} else {
			printer = msg -> player.sendSystemMessage(Component.literal(msg));
		}
		OverworldBiomeBuilder overworldBiomeBuilder = new OverworldBiomeBuilder();
		printDebugInfoTo(printer, blockPos.getX(), blockPos.getY(), blockPos.getZ(), overworldBiomeBuilder, c, e, t, h, w);
		
		return Command.SINGLE_SUCCESS;
	}
	
	private static void printDebugInfoTo(Consumer<String> printer, int x, int y, int z, OverworldBiomeBuilder overworldBiomeBuilder, float c, float e, float t, float h, float w) {
		printer.accept("The worldgen noise parameters of block position (%d, %d, %d) is:".formatted(x, y, z));
		printer.accept("  Continentalness: %-8.4f (%s)".formatted(c, overworldBiomeBuilder.getDebugStringForContinentalness(c)));
		printer.accept("          Erosion: %-8.4f (%s)".formatted(e, overworldBiomeBuilder.getDebugStringForErosion(e)));
		printer.accept("      Temperature: %-8.4f (%s)".formatted(t, overworldBiomeBuilder.getDebugStringForTemperature(t)));
		printer.accept("         Humidity: %-8.4f (%s)".formatted(h, overworldBiomeBuilder.getDebugStringForHumidity(h)));
		printer.accept("        Weirdness: %-8.4f (%s)".formatted(w, OverworldBiomeBuilder.getDebugStringForPeaksAndValleys(NoiseRouterData.peaksAndValleys(w))));
	}
}
