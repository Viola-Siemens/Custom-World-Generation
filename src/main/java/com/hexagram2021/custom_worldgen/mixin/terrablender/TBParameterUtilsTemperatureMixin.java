package com.hexagram2021.custom_worldgen.mixin.terrablender;

import com.hexagram2021.custom_worldgen.conditionalmixin.TerraBlenderClassLoadConditionTester;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import terrablender.api.ParameterUtils;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Restriction(require = @Condition(type = Condition.Type.TESTER, tester = TerraBlenderClassLoadConditionTester.class))
@Mixin(ParameterUtils.Temperature.class)
public class TBParameterUtilsTemperatureMixin {
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.45F))
	private static float modify1(float value) {
		return FROZEN_TEMPERATURE.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.15F))
	private static float modify2(float value) {
		return COOL_TEMPERATURE.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.2F))
	private static float modify3(float value) {
		return WARM_TEMPERATURE.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.55F))
	private static float modify4(float value) {
		return HOT_TEMPERATURE.value();
	}
}
