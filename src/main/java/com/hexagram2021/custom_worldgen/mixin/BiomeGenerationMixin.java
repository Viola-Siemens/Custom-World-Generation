package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Mixin(OverworldBiomeBuilder.class)
public class BiomeGenerationMixin {
	/*
	@Shadow @Final private Climate.Parameter[] temperatures;
	@Shadow @Final private Climate.Parameter[] humidities;
	@Shadow @Final private Climate.Parameter[] erosions;
	@Shadow @Final private Climate.Parameter FULL_RANGE;
	@Shadow @Final private Climate.Parameter FROZEN_RANGE;
	@Shadow @Final private Climate.Parameter UNFROZEN_RANGE;
	@Shadow @Final private Climate.Parameter mushroomFieldsContinentalness;
	@Shadow @Final private Climate.Parameter deepOceanContinentalness;
	@Shadow @Final private Climate.Parameter oceanContinentalness;
	@Shadow @Final private Climate.Parameter coastContinentalness;
	@Shadow @Final private Climate.Parameter inlandContinentalness;
	@Shadow @Final private Climate.Parameter nearInlandContinentalness;
	@Shadow @Final private Climate.Parameter midInlandContinentalness;
	@Shadow @Final private Climate.Parameter farInlandContinentalness;
	 */
	
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 1))
	public Climate.Parameter modifyFrozenTemperature(float from, float to) {
		return Climate.Parameter.span(-1.0F, FROZEN_TEMPERATURE.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 2))
	public Climate.Parameter modifyCoolTemperature(float from, float to) {
		return Climate.Parameter.span(FROZEN_TEMPERATURE.value(), COOL_TEMPERATURE.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 3))
	public Climate.Parameter modifyNeutralTemperature(float from, float to) {
		return Climate.Parameter.span(COOL_TEMPERATURE.value(), WARM_TEMPERATURE.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 4))
	public Climate.Parameter modifyWarmTemperature(float from, float to) {
		return Climate.Parameter.span(WARM_TEMPERATURE.value(), HOT_TEMPERATURE.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 5))
	public Climate.Parameter modifyHotTemperature(float from, float to) {
		return Climate.Parameter.span(HOT_TEMPERATURE.value(), 1.0F);
	}

	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 6))
	public Climate.Parameter modifyAridHumidity(float from, float to) {
		return Climate.Parameter.span(-1.0F, ARID_HUMIDITY.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 7))
	public Climate.Parameter modifyDryHumidity(float from, float to) {
		return Climate.Parameter.span(ARID_HUMIDITY.value(), DRY_HUMIDITY.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 8))
	public Climate.Parameter modifyNeutralHumidity(float from, float to) {
		return Climate.Parameter.span(DRY_HUMIDITY.value(), WET_HUMIDITY.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 9))
	public Climate.Parameter modifyWetHumidity(float from, float to) {
		return Climate.Parameter.span(WET_HUMIDITY.value(), HUMID_HUMIDITY.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 10))
	public Climate.Parameter modifyHumidHumidity(float from, float to) {
		return Climate.Parameter.span(HUMID_HUMIDITY.value(), 1.0F);
	}

	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 18))
	public Climate.Parameter modifyMushroomFieldsContinentalness(float from, float to) {
		return Climate.Parameter.span(-1.2F, MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 19))
	public Climate.Parameter modifyDeepOceanContinentalness(float from, float to) {
		return Climate.Parameter.span(MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value(), DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 20))
	public Climate.Parameter modifyOceanContinentalness(float from, float to) {
		return Climate.Parameter.span(DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.value(), OCEAN_TO_COAST_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 21))
	public Climate.Parameter modifyCoastContinentalness(float from, float to) {
		return Climate.Parameter.span(OCEAN_TO_COAST_CONTINENTALNESS.value(), COAST_TO_INLAND_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 22))
	public Climate.Parameter modifyInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), PEAKS_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 23))
	public Climate.Parameter modifyNearInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 24))
	public Climate.Parameter modifyMidInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 25))
	public Climate.Parameter modifyFarInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.value(), 1.2F);
	}
	
	/*
	@Inject(method = "<init>()V", at = @At(value = "TAIL"))
	public void printDebugInfo(CallbackInfo ci) {
		CWGLogger.LOGGER.info("FULL_RANGE = " + FULL_RANGE);
		CWGLogger.LOGGER.info("Temperatures:");
		for(Climate.Parameter parameter: this.temperatures) {
			CWGLogger.LOGGER.info(parameter.toString());
		}
		CWGLogger.LOGGER.info("Humidities:");
		for(Climate.Parameter parameter: this.humidities) {
			CWGLogger.LOGGER.info(parameter.toString());
		}
		CWGLogger.LOGGER.info("Erosions:");
		for(Climate.Parameter parameter: this.erosions) {
			CWGLogger.LOGGER.info(parameter.toString());
		}
		CWGLogger.LOGGER.info("FROZEN_RANGE = " + FROZEN_RANGE);
		CWGLogger.LOGGER.info("UNFROZEN_RANGE = " + UNFROZEN_RANGE);
		
		CWGLogger.LOGGER.info("Continentalnesses:");
		CWGLogger.LOGGER.info("mushroomFieldsContinentalness = " + this.mushroomFieldsContinentalness);
		CWGLogger.LOGGER.info("deepOceanContinentalness = " + this.deepOceanContinentalness);
		CWGLogger.LOGGER.info("oceanContinentalness = " + this.oceanContinentalness);
		CWGLogger.LOGGER.info("coastContinentalness = " + this.coastContinentalness);
		CWGLogger.LOGGER.info("inlandContinentalness = " + this.inlandContinentalness);
		CWGLogger.LOGGER.info("nearInlandContinentalness = " + this.nearInlandContinentalness);
		CWGLogger.LOGGER.info("midInlandContinentalness = " + this.midInlandContinentalness);
		CWGLogger.LOGGER.info("farInlandContinentalness = " + this.farInlandContinentalness);
	}
	 */
}
