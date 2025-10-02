package com.hexagram2021.custom_worldgen.mixin;

import com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.biome.TerrainShaper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TerrainShaper.class)
public abstract class WorldLoaderMixin {
	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 1
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyMushroomField2DeepOceanPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 2
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyDeepOcean2OceanPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.DEEP_OCEAN_TO_OCEAN_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 3
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyOcean2CoastPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.OCEAN_TO_COAST_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 4
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoastWaterPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.COAST_WATER_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 0
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoastBankPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.COAST_BANK_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 1
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoastLandPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.COAST_LAND_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 2
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyNearInlandPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.NEAR_INLAND_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 3
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyMidInlandPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.MID_INLAND_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 5
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyOcean2CoastErosionPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.OCEAN_TO_COAST_CONTINENTALNESS.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 5
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoastErosionPoint1(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.COAST_LAND_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 6
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoastErosionPoint2(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.NEAR_INLAND_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 7
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoast2InlandErosionPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 8
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyNearInlandErosionPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.INLAND_EROSION_SHAPER.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FFF)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 6
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyOcean2CoastJaggednessPoint(CubicSpline.Builder<C> instance, float location, float value, float derivative) {
		return instance.addPoint(CWGCommonConfig.COAST_TO_INLAND_CONTINENTALNESS.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 9
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyCoast2InlandJaggednessPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), value, derivative);
	}

	@Redirect(method = {"overworld"}, at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSpline$Builder;addPoint(FLnet/minecraft/util/CubicSpline;F)Lnet/minecraft/util/CubicSpline$Builder;",
			ordinal = 10
	))
	private static <C> CubicSpline.Builder<C> cwg$modifyMidInland2FarInlandJaggednessPoint(CubicSpline.Builder<C> instance, float location, CubicSpline<C> value, float derivative) {
		return instance.addPoint(CWGCommonConfig.PEAKS_EROSION_SHAPER.value(), value, derivative);
	}
}
