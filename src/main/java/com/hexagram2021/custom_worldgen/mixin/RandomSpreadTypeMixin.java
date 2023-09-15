package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.STRUCTURE_DENSITY_MULTIPLIER;

@Mixin(RandomSpreadType.class)
public class RandomSpreadTypeMixin {
	@Inject(method = "evaluate", at = @At(value = "RETURN"), cancellable = true)
	public void modifyEvaluate(RandomSource random, int spacing, CallbackInfoReturnable<Integer> cir) {
		int result = (int)(cir.getReturnValue() / STRUCTURE_DENSITY_MULTIPLIER.value());
		if(result <= 0) {
			result = 1;
		}
		cir.setReturnValue(result);
	}
}
