package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Mixin(TerrainProvider.class)
public class TerrainShaperMixin {
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 1))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyMushroomField2DeepOceanPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 2))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyDeepOcean2OceanPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(DEEP_OCEAN_TO_OCEAN_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 3))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyOcean2CoastPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(OCEAN_TO_COAST_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 4))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoastWaterPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(COAST_WATER_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 0))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoastBankPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(COAST_BANK_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 1))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoastLandPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(COAST_LAND_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 2))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyNearInlandPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(NEAR_INLAND_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 3))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyMidInlandPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(MID_INLAND_SHAPER.value(), _2);
	}

	@Redirect(method = "overworldFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 0))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyOcean2CoastErosionPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(OCEAN_TO_COAST_CONTINENTALNESS.value(), _2);
	}
	@Redirect(method = "overworldFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 0))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoastErosionPoint1(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(COAST_LAND_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 1))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoastErosionPoint2(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(NEAR_INLAND_SHAPER.value(), _2);
	}
	@Redirect(method = "overworldFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 2))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoast2InlandErosionPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), _2);
	}
	@Redirect(method = "overworldFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 3))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyNearInlandErosionPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(INLAND_EROSION_SHAPER.value(), _2);
	}

	@Redirect(method = "overworldJaggedness", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FF)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 0))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyOcean2CoastJaggednessPoint(CubicSpline.Builder<C, I> instance, float _1, float _2) {
		return instance.addPoint(COAST_TO_INLAND_CONTINENTALNESS.value(), _2);
	}
	@Redirect(method = "overworldJaggedness", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 0))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyCoast2InlandJaggednessPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), _2);
	}
	@Redirect(method = "overworldJaggedness", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;)Lnet/minecraft/util/CubicSpline$Builder;", ordinal = 1))
	private static <C, I extends ToFloatFunction<C>> CubicSpline.Builder<C, I> modifyMidInland2FarInlandJaggednessPoint(CubicSpline.Builder<C, I> instance, float _1, CubicSpline<C, I> _2) {
		return instance.addPoint(PEAKS_EROSION_SHAPER.value(), _2);
	}
}
