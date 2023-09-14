package com.hexagram2021.custom_worldgen.mixin.accessors;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(NoiseGeneratorSettings.class)
public interface NoiseGeneratorSettingsAccess {
	@Accessor("spawnTarget")
	@Mutable
	void setSpawnTarget(List<Climate.ParameterPoint> spawnTarget);
}
