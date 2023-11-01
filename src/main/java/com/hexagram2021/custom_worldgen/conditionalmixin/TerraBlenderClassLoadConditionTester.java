package com.hexagram2021.custom_worldgen.conditionalmixin;

import com.hexagram2021.custom_worldgen.common.utils.CWGLogger;
import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester;

public class TerraBlenderClassLoadConditionTester implements ConditionTester {
	@Override
	public boolean isSatisfied(String mixinClassName) {
		try {
			Class.forName("terrablender.api.ParameterUtils");
		} catch (ClassNotFoundException e) {
			return false;
		}
		CWGLogger.LOGGER.debug("Terrablender founded, loading compat mixin %s.".formatted(mixinClassName));
		return true;
	}
}
