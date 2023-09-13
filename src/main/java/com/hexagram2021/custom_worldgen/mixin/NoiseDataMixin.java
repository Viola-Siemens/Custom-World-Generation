package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.data.worldgen.NoiseData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Mixin(NoiseData.class)
public class NoiseDataMixin {
	@ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -10, ordinal = 0))
	private static int modifyTemperatureOctave(int constant) {
		return constant + OCTAVE_TEMPERATURE_ADDER.value();
	}
	@ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -8, ordinal = 0))
	private static int modifyHumidityOctave(int constant) {
		return constant + OCTAVE_HUMIDITY_ADDER.value();
	}
	@ModifyConstant(method = "registerBiomeNoises", constant = @Constant(intValue = -9, ordinal = 0))
	private static int modifyContinentalnessOctave(int constant) {
		return constant + OCTAVE_CONTINENTALNESS_ADDER.value();
	}
}