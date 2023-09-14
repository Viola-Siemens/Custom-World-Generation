package com.hexagram2021.custom_worldgen.mixin;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.custom_worldgen.common.CWGLogger;
import com.hexagram2021.custom_worldgen.mixin.accessors.CubicSplineMultipointAccessor;
import com.hexagram2021.custom_worldgen.mixin.accessors.NoiseGeneratorSettingsAccess;
import com.hexagram2021.custom_worldgen.mixin.accessors.NoiseParametersAccess;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Consumer;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.*;

@Mixin(WorldLoader.class)
public abstract class WorldLoaderMixin {
	@Shadow
	private static LayeredRegistryAccess<RegistryLayer> loadAndReplaceLayer(ResourceManager p_249913_, LayeredRegistryAccess<RegistryLayer> p_252077_, RegistryLayer p_250346_, List<RegistryDataLoader.RegistryData<?>> p_250589_) {
		throw new UnsupportedOperationException("Replaced by Mixin");
	}

	private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

	@Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/WorldLoader;loadAndReplaceLayer(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/core/LayeredRegistryAccess;Lnet/minecraft/server/RegistryLayer;Ljava/util/List;)Lnet/minecraft/core/LayeredRegistryAccess;"))
	private static LayeredRegistryAccess<RegistryLayer> modifyLoadedParameters(ResourceManager resourceManager, LayeredRegistryAccess<RegistryLayer> registryAccess,
																			   RegistryLayer registryLayer, List<RegistryDataLoader.RegistryData<?>> registryData) {
		LayeredRegistryAccess<RegistryLayer> res = loadAndReplaceLayer(resourceManager, registryAccess, registryLayer, registryData);
		RegistryAccess.Frozen frozen = res.getLayer(registryLayer);

		//Noise
		Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = frozen.registryOrThrow(Registries.NOISE);
		NormalNoise.NoiseParameters temperature = noiseParametersRegistry.getOrThrow(Noises.TEMPERATURE);
		NormalNoise.NoiseParameters temperatureLarge = noiseParametersRegistry.getOrThrow(Noises.TEMPERATURE_LARGE);
		NormalNoise.NoiseParameters humidity = noiseParametersRegistry.getOrThrow(Noises.VEGETATION);
		NormalNoise.NoiseParameters humidityLarge = noiseParametersRegistry.getOrThrow(Noises.VEGETATION_LARGE);
		NormalNoise.NoiseParameters continentalness = noiseParametersRegistry.getOrThrow(Noises.CONTINENTALNESS);
		NormalNoise.NoiseParameters continentalnessLarge = noiseParametersRegistry.getOrThrow(Noises.CONTINENTALNESS_LARGE);

		((NoiseParametersAccess)(Object) temperature).setFirstOctave(temperature.firstOctave() + OCTAVE_TEMPERATURE_ADDER.value());
		((NoiseParametersAccess)(Object) temperatureLarge).setFirstOctave(temperatureLarge.firstOctave() + OCTAVE_TEMPERATURE_ADDER.value());
		((NoiseParametersAccess)(Object) humidity).setFirstOctave(humidity.firstOctave() + OCTAVE_HUMIDITY_ADDER.value());
		((NoiseParametersAccess)(Object) humidityLarge).setFirstOctave(humidityLarge.firstOctave() + OCTAVE_HUMIDITY_ADDER.value());
		((NoiseParametersAccess)(Object) continentalness).setFirstOctave(continentalness.firstOctave() + OCTAVE_CONTINENTALNESS_ADDER.value());
		((NoiseParametersAccess)(Object) continentalnessLarge).setFirstOctave(continentalnessLarge.firstOctave() + OCTAVE_CONTINENTALNESS_ADDER.value());

		//Spawn Target
		Registry<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = frozen.registryOrThrow(Registries.NOISE_SETTINGS);
		NoiseGeneratorSettings overworld = noiseGeneratorSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
		ImmutableList.Builder<Climate.ParameterPoint> builder = ImmutableList.builder();
		Climate.Parameter surface = Climate.Parameter.point(0.0F);
		float weirdnessGap = 0.16F;
		builder.add(new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), 1.0F), FULL_RANGE, surface, Climate.Parameter.span(-1.0F, -weirdnessGap), 0L));
		builder.add(new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(COAST_TO_INLAND_CONTINENTALNESS.value(), 1.0F), FULL_RANGE, surface, Climate.Parameter.span(weirdnessGap, 1.0F), 0L));
		if(ENABLE_MUSHROOM_FIELDS_SPAWN.value()) {
			builder.add(new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(-1.2F, MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value()), FULL_RANGE, surface, Climate.Parameter.span(-1.0F, -weirdnessGap), 0L));
			builder.add(new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(-1.2F, MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.value()), FULL_RANGE, surface, Climate.Parameter.span(weirdnessGap, 1.0F), 0L));
		}
		((NoiseGeneratorSettingsAccess)(Object) overworld).setSpawnTarget(builder.build());

		//TerrainShaper
		Registry<DensityFunction> densityFunctionRegistry = frozen.registryOrThrow(Registries.DENSITY_FUNCTION);
		DensityFunction overworldOffset = densityFunctionRegistry.getOrThrow(NoiseRouterData.OFFSET);
		DensityFunction overworldLargeOffset = densityFunctionRegistry.getOrThrow(NoiseRouterData.OFFSET_LARGE);
		DensityFunction overworldAmplifiedOffset = densityFunctionRegistry.getOrThrow(NoiseRouterData.OFFSET_AMPLIFIED);
		DensityFunction overworldFactor = densityFunctionRegistry.getOrThrow(NoiseRouterData.FACTOR);
		DensityFunction overworldLargeFactor = densityFunctionRegistry.getOrThrow(NoiseRouterData.FACTOR_LARGE);
		DensityFunction overworldAmplifiedFactor = densityFunctionRegistry.getOrThrow(NoiseRouterData.FACTOR_AMPLIFIED);
		DensityFunction overworldJaggedness = densityFunctionRegistry.getOrThrow(NoiseRouterData.JAGGEDNESS);
		DensityFunction overworldLargeJaggedness = densityFunctionRegistry.getOrThrow(NoiseRouterData.JAGGEDNESS_LARGE);
		DensityFunction overworldAmplifiedJaggedness = densityFunctionRegistry.getOrThrow(NoiseRouterData.JAGGEDNESS_AMPLIFIED);

		modifyOverworldOffset(overworldOffset);
		modifyOverworldOffset(overworldLargeOffset);
		modifyOverworldOffset(overworldAmplifiedOffset);
		modifyOverworldFactor(overworldFactor);
		modifyOverworldFactor(overworldLargeFactor);
		modifyOverworldFactor(overworldAmplifiedFactor);
		modifyOverworldJaggedness(overworldJaggedness);
		modifyOverworldJaggedness(overworldLargeJaggedness);
		modifyOverworldJaggedness(overworldAmplifiedJaggedness);

		CWGLogger.LOGGER.debug("Successfully applied config values to worldgen.");
		return res;
	}

	private static void modifyOverworldOffset(DensityFunction offset) {
		convertDirectWrappedDensityFunctionAndRun(
				offset, flat_cache -> convertWrappedDensityFunctionAndRun(
						flat_cache.wrapped(), cache_2d -> convertAp2DensityFunctionAndRunRight(
								cache_2d.wrapped(), mul -> convertAp2DensityFunctionAndRunLeft(
										mul, add -> convertAp2DensityFunctionAndRunRight(
												add, spline -> {
													if(spline instanceof DensityFunctions.HolderHolder holderHolder &&
															holderHolder.function().get() instanceof DensityFunctions.Spline continents) {
														if(continents.spline() instanceof CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
															solveTerrainOffsetCubicSplines(multipoint);
														} else {
															throw new ClassCastException("Cannot convert " + continents.spline() + " to " + CubicSpline.Multipoint.class.getName());
														}
													} else {
														throw new ClassCastException("Cannot convert " + spline + " to " + DensityFunctions.Spline.class.getName());
													}
												}
										)
								)
						)
				)
		);
	}

	private static void modifyOverworldFactor(DensityFunction factor) {
		convertDirectWrappedDensityFunctionAndRun(
				factor, flat_cache -> convertWrappedDensityFunctionAndRun(
						flat_cache.wrapped(), cache_2d -> convertAp2DensityFunctionAndRunRight(
								cache_2d.wrapped(), mul -> convertAp2DensityFunctionAndRunRight(
										mul, add -> convertAp2DensityFunctionAndRunRight(
												add, spline -> {
													if(spline instanceof DensityFunctions.HolderHolder holderHolder &&
															holderHolder.function().get() instanceof DensityFunctions.Spline continents) {
														if(continents.spline() instanceof CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
															solveTerrainFactorCubicSplines(multipoint);
														} else {
															throw new ClassCastException("Cannot convert " + continents.spline() + " to " + CubicSpline.Multipoint.class.getName());
														}
													} else {
														throw new ClassCastException("Cannot convert " + spline + " to " + DensityFunctions.Spline.class.getName());
													}
												}
										)
								)
						)
				)
		);
	}

	private static void modifyOverworldJaggedness(DensityFunction factor) {
		convertDirectWrappedDensityFunctionAndRun(
				factor, flat_cache -> convertWrappedDensityFunctionAndRun(
						flat_cache.wrapped(), cache_2d -> convertAp2DensityFunctionAndRunRight(
								cache_2d.wrapped(), mul -> convertAp2DensityFunctionAndRunRight(
										mul, add -> convertAp2DensityFunctionAndRunRight(
												add, spline -> {
													if(spline instanceof DensityFunctions.HolderHolder holderHolder &&
															holderHolder.function().get() instanceof DensityFunctions.Spline continents) {
														if(continents.spline() instanceof CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
															solveTerrainJaggednessCubicSplines(multipoint);
														} else {
															throw new ClassCastException("Cannot convert " + continents.spline() + " to " + CubicSpline.Multipoint.class.getName());
														}
													} else {
														throw new ClassCastException("Cannot convert " + spline + " to " + DensityFunctions.Spline.class.getName());
													}
												}
										)
								)
						)
				)
		);
	}

	private static final float[] offsetLocations = new float[] {
			-1.1F, -1.02F, -0.51F, -0.44F, -0.18F,
			-0.16F, -0.15F, -0.1F, 0.25F, 1.0F
	};
	private static final float[] factorLocations = new float[] {
			-0.19F, -0.15F, -0.1F, 0.03F, 0.06F
	};
	private static final float[] jaggednessLocations = new float[] {
			-0.11F, 0.03F, 0.65F
	};

	private static void equalsOrThrow(float origin, float checked) {
		float diff = origin - checked;
		if(diff < -1e-5F || diff > 1e-5F) {
			throw new IllegalStateException("Failed to check compatibility: location values changed by datapacks. Please do NOT install other datapacks to modify terrain with our Custom World Generation Mod.");
		}
	}

	private static void solveTerrainOffsetCubicSplines(CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
		float[] newLocations = new float[] {
				offsetLocations[0], MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER.value(),
				DEEP_OCEAN_TO_OCEAN_SHAPER.value(), OCEAN_TO_COAST_SHAPER.value(),
				COAST_WATER_SHAPER.value(), COAST_BANK_SHAPER.value(),
				COAST_LAND_SHAPER.value(), NEAR_INLAND_SHAPER.value(),
				MID_INLAND_SHAPER.value(), offsetLocations[9]
		};
		if(multipoint.locations().length != offsetLocations.length) {
			throw new IllegalStateException("Failed to check compatibility: cubic spline location count changed by datapacks. Please do NOT install other datapacks to modify terrain with our Custom World Generation Mod.");
		}
		for(int i = 0; i < multipoint.locations().length; ++i) {
			equalsOrThrow(multipoint.locations()[i], offsetLocations[i]);
		}
		((CubicSplineMultipointAccessor)(Object)multipoint).setLocations(newLocations);
	}

	private static void solveTerrainFactorCubicSplines(CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
		float[] newLocations = new float[] {
				OCEAN_TO_COAST_CONTINENTALNESS.value(), COAST_LAND_SHAPER.value(),
				NEAR_INLAND_SHAPER.value(), NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(),
				INLAND_EROSION_SHAPER.value()
		};
		if(multipoint.locations().length != factorLocations.length) {
			throw new IllegalStateException("Failed to check compatibility: cubic spline location count changed by datapacks. Please do NOT install other datapacks to modify terrain with our Custom World Generation Mod.");
		}
		for(int i = 0; i < multipoint.locations().length; ++i) {
			equalsOrThrow(multipoint.locations()[i], factorLocations[i]);
		}
		((CubicSplineMultipointAccessor)(Object)multipoint).setLocations(newLocations);
	}

	private static void solveTerrainJaggednessCubicSplines(CubicSpline.Multipoint<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> multipoint) {
		float[] newLocations = new float[] {
				COAST_TO_INLAND_CONTINENTALNESS.value(), NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.value(),
				PEAKS_EROSION_SHAPER.value()
		};
		if(multipoint.locations().length != jaggednessLocations.length) {
			throw new IllegalStateException("Failed to check compatibility: cubic spline location count changed by datapacks. Please do NOT install other datapacks to modify terrain with our Custom World Generation Mod.");
		}
		for(int i = 0; i < multipoint.locations().length; ++i) {
			equalsOrThrow(multipoint.locations()[i], jaggednessLocations[i]);
		}
		((CubicSplineMultipointAccessor)(Object)multipoint).setLocations(newLocations);
	}

	private static void convertDirectWrappedDensityFunctionAndRun(DensityFunction densityFunction, Consumer<DensityFunctions.MarkerOrMarked> consumer) {
		if(densityFunction instanceof DensityFunctions.MarkerOrMarked converted) {
			consumer.accept(converted);
		} else {
			throw new ClassCastException("Cannot convert " + densityFunction + " to " + DensityFunctions.MarkerOrMarked.class.getName());
		}
	}

	private static void convertWrappedDensityFunctionAndRun(DensityFunction densityFunction, Consumer<DensityFunctions.MarkerOrMarked> consumer) {
		if(densityFunction instanceof DensityFunctions.HolderHolder holderHolder &&
				holderHolder.function().get() instanceof DensityFunctions.MarkerOrMarked converted) {
			consumer.accept(converted);
		} else {
			throw new ClassCastException("Cannot convert " + densityFunction + " to " + DensityFunctions.MarkerOrMarked.class.getName());
		}
	}

	private static void convertAp2DensityFunctionAndRunLeft(DensityFunction densityFunction, Consumer<DensityFunction> consumer) {
		if(densityFunction instanceof DensityFunctions.HolderHolder holderHolder &&
				holderHolder.function().get()  instanceof DensityFunctions.TwoArgumentSimpleFunction ap2) {
			consumer.accept(ap2.argument1());
		} else {
			throw new ClassCastException("Cannot convert " + densityFunction + " to " + DensityFunctions.TwoArgumentSimpleFunction.class.getName());
		}
	}

	private static void convertAp2DensityFunctionAndRunRight(DensityFunction densityFunction, Consumer<DensityFunction> consumer) {
		if(densityFunction instanceof DensityFunctions.HolderHolder holderHolder &&
				holderHolder.function().get()  instanceof DensityFunctions.TwoArgumentSimpleFunction ap2) {
			consumer.accept(ap2.argument2());
		} else {
			throw new ClassCastException("Cannot convert " + densityFunction + " to " + DensityFunctions.TwoArgumentSimpleFunction.class.getName());
		}
	}
}
