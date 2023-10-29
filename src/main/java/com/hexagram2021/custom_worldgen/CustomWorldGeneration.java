package com.hexagram2021.custom_worldgen;

import com.hexagram2021.custom_worldgen.server.CWGCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(CustomWorldGeneration.MODID)
public class CustomWorldGeneration {
	public static final String MODID = "custom_worldgen";

	public CustomWorldGeneration() {
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		dispatcher.register(CWGCommands.register());
	}
}
