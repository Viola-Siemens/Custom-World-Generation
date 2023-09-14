package com.hexagram2021.custom_worldgen.mixin.accessors;

import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NormalNoise.NoiseParameters.class)
public interface NoiseParametersAccess {
	@Accessor("firstOctave")
	@Mutable
	void setFirstOctave(int firstOctave);
}
