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
@Mixin(ParameterUtils.Humidity.class)
public class TBParameterUtilsHumidityMixin {
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.35F))
	private static float modify1(float value) {
		return ARID_HUMIDITY.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -0.1F))
	private static float modify2(float value) {
		return DRY_HUMIDITY.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.1F))
	private static float modify3(float value) {
		return WET_HUMIDITY.value();
	}
	@ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.3F))
	private static float modify4(float value) {
		return HUMID_HUMIDITY.value();
	}
}
