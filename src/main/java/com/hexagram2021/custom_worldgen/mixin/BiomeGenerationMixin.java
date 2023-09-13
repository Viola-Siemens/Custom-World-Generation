package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Mixin(OverworldBiomeBuilder.class)
public class BiomeGenerationMixin {
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 18))
	public Climate.Parameter modifyMushroomFieldsContinentalness(float from, float to) {
		return Climate.Parameter.span(-1.2F, MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 19))
	public Climate.Parameter modifyDeepOceanContinentalness(float from, float to) {
		return Climate.Parameter.span(MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value(), DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 20))
	public Climate.Parameter modifyOceanContinentalness(float from, float to) {
		return Climate.Parameter.span(DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.value(), OCEAN_TO_COAST_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 21))
	public Climate.Parameter modifyCoastContinentalness(float from, float to) {
		return Climate.Parameter.span(OCEAN_TO_COAST_CONTINENTALNESS.value(), COAST_TO_INLAND_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 22))
	public Climate.Parameter modifyInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), PEAKS_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 23))
	public Climate.Parameter modifyNearInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 24))
	public Climate.Parameter modifyMidInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(), MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.value());
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Climate$Parameter;span(FF)Lnet/minecraft/world/level/biome/Climate$Parameter;", ordinal = 25))
	public Climate.Parameter modifyFarInlandContinentalness(float from, float to) {
		return Climate.Parameter.span(MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.value(), 1.2F);
	}
}
