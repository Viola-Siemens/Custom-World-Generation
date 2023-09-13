package com.hexagram2021.custom_worldgen;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CustomWorldGeneration.MODID)
public class CustomWorldGeneration {
	public static final String MODID = "custom_worldgen";

	public CustomWorldGeneration() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	}
}
