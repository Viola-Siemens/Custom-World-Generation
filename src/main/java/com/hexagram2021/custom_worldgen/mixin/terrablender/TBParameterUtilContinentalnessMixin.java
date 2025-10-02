package com.hexagram2021.custom_worldgen.mixin.terrablender;

import com.hexagram2021.custom_worldgen.conditionalmixin.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import terrablender.api.ParameterUtils;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Restriction(classNames = {"terrablender.api.ParameterUtils"})
@Mixin(ParameterUtils.Continentalness.class)
public class TBParameterUtilContinentalnessMixin {
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -1.05F))
	private static float modify1(float value) {
		return MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.455F))
	private static float modify2(float value) {
		return DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.19F))
	private static float modify3(float value) {
		return OCEAN_TO_COAST_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.11F))
	private static float modify4(float value) {
		return COAST_TO_INLAND_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.03F))
	private static float modify5(float value) {
		return NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.3F))
	private static float modify6(float value) {
		return MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.55F))
	private static float modify7(float value) {
		return PEAKS_CONTINENTALNESS.value();
	}
}
