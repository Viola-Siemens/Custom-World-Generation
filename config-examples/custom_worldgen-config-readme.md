# Temperature

## Description

The temperature values MUST BE ASCENDING!

Temperature in [-1.0, FROZEN_TEMPERATURE] will be filled with frozen biomes (eg. Snowy Plains, Frozen Ocean), [FROZEN_TEMPERATURE, COOL_TEMPERATURE] is for cold biomes (eg. Taiga, Cold Ocean), [COOL_TEMPERATURE, WARM_TEMPERATURE] is for neutral biomes (eg. Plains, Forests), [WARM_TEMPERATURE, HOT_TEMPERATURE] is for warm biomes (eg. savanna, jungle), and [HOT_TEMPERATURE, 1.0] is for hot biomes (eg. badlands, desert).

## Presets

### Vanilla

```json
{
	"FROZEN_TEMPERATURE": -0.45,
	"COOL_TEMPERATURE": -0.15,
	"WARM_TEMPERATURE": 0.2,
	"HOT_TEMPERATURE": 0.55,
	"OCTAVE_TEMPERATURE_ADDER": 0
}
```

# Humidity

## Description

The humidity values MUST BE ASCENDING!

Temperature in [-1.0, ARID_HUMIDITY] will be filled with arid biomes (eg. ice spikes, savanna), [ARID_HUMIDITY, DRY_HUMIDITY] is for dry biomes (eg. plains, windswept gravelly hills), [DRY_HUMIDITY, WET_HUMIDITY] is for neutral biomes (eg. meadows, forests), [WET_HUMIDITY, HUMID_HUMIDITY] is for wet biomes (eg. taiga, wooded badlands), and [HUMID_HUMIDITY, 1.0] is for humid biomes (eg. dark forests, jungle).

## Presets

### Vanilla

```json
{
	"ARID_HUMIDITY": -0.35,
	"DRY_HUMIDITY": -0.1,
	"WET_HUMIDITY": 0.1,
	"HUMID_HUMIDITY": 0.3,
	"OCTAVE_HUMIDITY_ADDER": 0
}
```

# Continentalness

## Config Generator

If you are confused about continentalness points and terrain generation, you can try our [Config Generator](https://viola-siemens.github.io/pages/tools/oceanworld-config.html) to generate config and see the preview of the config you make.

## Description

The continentalness values and shaper values MUST BE ASCENDING!

If you modify these continentalness values, please remember to modify shaper values under the guide.

## Presets

### Default Values in Ocean World Mod

```json
{
	"MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS": -1.01,
	"DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS": 0.29,
	"OCEAN_TO_COAST_CONTINENTALNESS": 0.42,
	"COAST_TO_INLAND_CONTINENTALNESS": 0.51,
	"NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS": 0.62,
	"MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS": 0.785,
	"PEAKS_CONTINENTALNESS": 0.9,

	"MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER": -0.99,
	"DEEP_OCEAN_TO_OCEAN_SHAPER": 0.25,
	"OCEAN_TO_COAST_SHAPER": 0.325,
	"COAST_WATER_SHAPER": 0.43,
	"COAST_BANK_SHAPER": 0.47,
	"COAST_LAND_SHAPER": 0.48,
	"NEAR_INLAND_SHAPER": 0.52,
	"INLAND_EROSION_SHAPER": 0.64,
	"MID_INLAND_SHAPER": 0.76,
	"PEAKS_EROSION_SHAPER": 0.92,

	"OCTAVE_CONTINENTALNESS_ADDER": 1
}
```

Notice that the larger gap between two neighbor values, the more frequently it will generate. The example given above means deep ocean (-1.01~0.29) will generate most frequently.

### Vanilla

```json
{
	"MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS": -1.05,
	"DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS": -0.455,
	"OCEAN_TO_COAST_CONTINENTALNESS": -0.19,
	"COAST_TO_INLAND_CONTINENTALNESS": -0.11,
	"NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS": 0.03,
	"MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS": 0.3,
	"PEAKS_CONTINENTALNESS": 0.55,

	"MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER": -1.02,
	"DEEP_OCEAN_TO_OCEAN_SHAPER": -0.51,
	"OCEAN_TO_COAST_SHAPER": -0.44,
	"COAST_WATER_SHAPER": -0.18,
	"COAST_BANK_SHAPER": -0.16,
	"COAST_LAND_SHAPER": -0.15,
	"NEAR_INLAND_SHAPER": -0.1,
	"INLAND_EROSION_SHAPER": 0.06,
	"MID_INLAND_SHAPER": 0.25,
	"PEAKS_EROSION_SHAPER": 0.65,

	"OCTAVE_CONTINENTALNESS_ADDER": 0
}
```

## Comments

Here are some constructive comments for you to modify the shaper values:

- MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER should be greater than MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.
- DEEP_OCEAN_TO_OCEAN_SHAPER should be a little less than DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.
- OCEAN_TO_COAST_SHAPER should be between DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS and OCEAN_TO_COAST_CONTINENTALNESS.
- COAST_WATER_SHAPER should be close to OCEAN_TO_COAST_CONTINENTALNESS.
- COAST_BANK_SHAPER should be between OCEAN_TO_COAST_CONTINENTALNESS and COAST_TO_INLAND_CONTINENTALNESS.
- COAST_LAND_SHAPER should be a little greater than COAST_BANK_SHAPER.
- NEAR_INLAND_SHAPER should be a little greater than COAST_TO_INLAND_CONTINENTALNESS.
- INLAND_EROSION_SHAPER should be a little greater than NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.
- MID_INLAND_SHAPER should be a little less than MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.
- PEAKS_EROSION_SHAPER should be a little greater than PEAKS_CONTINENTALNESS.

# Others

## STRUCTURE_DENSITY_MULTIPLIER

The higher, the denser the structures will be. The lower, the less chance you can find structures in your world.

## ENABLE_MUSHROOM_FIELDS_SPAWN

If true, players can spawn at mushroom fields.

