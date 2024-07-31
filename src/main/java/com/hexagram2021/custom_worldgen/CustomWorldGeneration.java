package com.hexagram2021.custom_worldgen;

import com.hexagram2021.custom_worldgen.server.CWGCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(CustomWorldGeneration.MODID)
public class CustomWorldGeneration {
	public static final String MODID = "custom_worldgen";

	public CustomWorldGeneration() {
		NeoForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		dispatcher.register(CWGCommands.register());
	}
}
