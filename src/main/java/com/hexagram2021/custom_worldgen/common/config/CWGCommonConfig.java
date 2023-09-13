package com.hexagram2021.custom_worldgen.common.config;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hexagram2021.custom_worldgen.common.CWGLogger;

import java.io.*;
import java.util.List;

import static com.hexagram2021.custom_worldgen.CustomWorldGeneration.MODID;

public class CWGCommonConfig {
	public interface IConfigValue<T extends Number> {
		List<IConfigValue<?>> configValues = Lists.newArrayList();

		String name();
		T value();
		void parseAsValue(JsonElement element);

		void checkValueRange() throws ConfigValueException;
	}

	public static class FloatConfigValue implements IConfigValue<Float> {
		private final String name;
		private float value;
		private final float min;
		private final float max;

		public FloatConfigValue(String name, float value, float min, float max) {
			this.name = name;
			this.value = value;
			this.min = min;
			this.max = max;

			configValues.add(this);
		}

		@Override
		public void checkValueRange() throws ConfigValueException {
			if(this.value > this.max || this.value < this.min) {
				throw new ConfigValueException(this.name + " is not in range [%f, %f]! Please check your config file.".formatted(this.min, this.max));
			}
		}

		@Override
		public void parseAsValue(JsonElement element) {
			this.value = element.getAsFloat();
		}

		@Override
		public String name() {
			return this.name;
		}

		@Override
		public Float value() {
			return this.value;
		}
	}

	public static class IntConfigValue implements IConfigValue<Integer> {
		private final String name;
		private int value;
		private final int min;
		private final int max;

		public IntConfigValue(String name, int value, int min, int max) {
			this.name = name;
			this.value = value;
			this.min = min;
			this.max = max;

			configValues.add(this);
		}

		@Override
		public void checkValueRange() throws ConfigValueException {
			if(this.value > this.max || this.value < this.min) {
				throw new ConfigValueException(this.name + " is not in range [%d, %d]! Please check your config file.".formatted(this.min, this.max));
			}
		}

		@Override
		public void parseAsValue(JsonElement element) {
			this.value = element.getAsInt();
		}

		@Override
		public String name() {
			return this.name;
		}

		@Override
		public Integer value() {
			return this.value;
		}
	}

	public static final File filePath = new File("./config/");
	private static final File configFile = new File(filePath + "/" + MODID + "-config.json");
	private static final File readmeFile = new File(filePath + "/" + MODID + "-config-readme.md");

	//Continentalness Points
	public static final FloatConfigValue MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS = new FloatConfigValue("MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS", -1.01F, -1.2F, 1.2F);
	public static final FloatConfigValue DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS = new FloatConfigValue("DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS", 0.29F, -1.2F, 1.2F);
	public static final FloatConfigValue OCEAN_TO_COAST_CONTINENTALNESS = new FloatConfigValue("OCEAN_TO_COAST_CONTINENTALNESS", 0.42F, -1.2F, 1.2F);
	public static final FloatConfigValue COAST_TO_INLAND_CONTINENTALNESS = new FloatConfigValue("COAST_TO_INLAND_CONTINENTALNESS", 0.51F, -1.2F, 1.2F);
	public static final FloatConfigValue NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS = new FloatConfigValue("NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS", 0.62F, -1.2F, 1.2F);
	public static final FloatConfigValue MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS = new FloatConfigValue("MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS", 0.785F, -1.2F, 1.2F);
	public static final FloatConfigValue PEAKS_CONTINENTALNESS = new FloatConfigValue("PEAKS_CONTINENTALNESS", 0.9F, -1.2F, 1.2F);

	//Shaper Points
	public static final FloatConfigValue MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER = new FloatConfigValue("MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER", -0.99F, -1.2F, 1.2F);
	public static final FloatConfigValue DEEP_OCEAN_TO_OCEAN_SHAPER = new FloatConfigValue("DEEP_OCEAN_TO_OCEAN_SHAPER", 0.25F, -1.2F, 1.2F);
	public static final FloatConfigValue OCEAN_TO_COAST_SHAPER = new FloatConfigValue("OCEAN_TO_COAST_SHAPER", 0.325F, -1.2F, 1.2F);
	public static final FloatConfigValue COAST_WATER_SHAPER = new FloatConfigValue("COAST_WATER_SHAPER", 0.43F, -1.2F, 1.2F);
	public static final FloatConfigValue COAST_BANK_SHAPER = new FloatConfigValue("COAST_BANK_SHAPER", 0.47F, -1.2F, 1.2F);
	public static final FloatConfigValue COAST_LAND_SHAPER = new FloatConfigValue("COAST_LAND_SHAPER", 0.48F, -1.2F, 1.2F);
	public static final FloatConfigValue NEAR_INLAND_SHAPER = new FloatConfigValue("NEAR_INLAND_SHAPER", 0.52F, -1.2F, 1.2F);
	public static final FloatConfigValue INLAND_EROSION_SHAPER = new FloatConfigValue("INLAND_EROSION_SHAPER", 0.64F, -1.2F, 1.2F);
	public static final FloatConfigValue MID_INLAND_SHAPER = new FloatConfigValue("MID_INLAND_SHAPER", 0.76F, -1.2F, 1.2F);
	public static final FloatConfigValue PEAKS_EROSION_SHAPER = new FloatConfigValue("PEAKS_EROSION_SHAPER", 0.92F, -1.2F, 1.2F);

	//Octaves
	public static final IntConfigValue OCTAVE_CONTINENTALNESS_ADDER = new IntConfigValue("OCTAVE_CONTINENTALNESS_ADDER", 1, -8, 8);

	static {
		lazyInit();
	}

	private static void lazyInit() {
		try {
			if (!filePath.exists() && !filePath.mkdir()) {
				CWGLogger.LOGGER.error("Could not mkdir " + filePath);
			} else {
				if (configFile.exists()) {
					try(Reader reader = new FileReader(configFile)) {
						JsonElement json = JsonParser.parseReader(reader);
						loadFromJson(json.getAsJsonObject());
					}
					checkValues();
					saveConfig();
				} else {
					if (configFile.createNewFile()) {
						saveConfig();
					} else {
						CWGLogger.LOGGER.error("Could not create new file " + configFile);
					}
				}
				if(!readmeFile.exists()) {
					if (readmeFile.createNewFile()) {
						fillReadmeFile();
					} else {
						CWGLogger.LOGGER.error("Could not create new file " + readmeFile);
					}
				}
			}
		} catch (IOException e) {
			CWGLogger.LOGGER.error("Error during loading config.", e);
		}
	}

	private static void fillReadmeFile() throws IOException {
		try(Writer writer = new FileWriter(readmeFile)) {
			writer.write("## Config Generator\n\n");
			writer.write("You can use our [Config Generator](https://viola-siemens.github.io/pages/tools/oceanworld-config.html) to generate config and see the preview of the config you make.\n\n");
			writer.write("## Description\n\n");
			writer.write("The continentalness values and shaper values MUST BE ASCENDING!\n\n");
			writer.write("If you modify these continentalness values, please remember to modify shaper values under the guide.\n\n");
			writer.write("## Presets\n\n");
			writer.write("### Default values\n\n");
			writer.write("```json\n{\n");
			writer.write("\t\"MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS\": -1.01,\n");
			writer.write("\t\"DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS\": 0.29,\n");
			writer.write("\t\"OCEAN_TO_COAST_CONTINENTALNESS\": 0.42,\n");
			writer.write("\t\"COAST_TO_INLAND_CONTINENTALNESS\": 0.51,\n");
			writer.write("\t\"NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS\": 0.62,\n");
			writer.write("\t\"MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS\": 0.785,\n");
			writer.write("\t\"PEAKS_CONTINENTALNESS\": 0.9,\n\n");
			writer.write("\t\"MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER\": -0.99,\n");
			writer.write("\t\"DEEP_OCEAN_TO_OCEAN_SHAPER\": 0.25,\n");
			writer.write("\t\"OCEAN_TO_COAST_SHAPER\": 0.325,\n");
			writer.write("\t\"COAST_WATER_SHAPER\": 0.43,\n");
			writer.write("\t\"COAST_BANK_SHAPER\": 0.47,\n");
			writer.write("\t\"COAST_LAND_SHAPER\": 0.48,\n");
			writer.write("\t\"NEAR_INLAND_SHAPER\": 0.52,\n");
			writer.write("\t\"INLAND_EROSION_SHAPER\": 0.64,\n");
			writer.write("\t\"MID_INLAND_SHAPER\": 0.76,\n");
			writer.write("\t\"PEAKS_EROSION_SHAPER\": 0.92,\n\n");
			writer.write("\t\"OCTAVE_CONTINENTALNESS_ADDER\": 1\n");
			writer.write("}\n```\n\n");
			writer.write("Notice that the larger gap between two neighbor values, the more frequently it will generate. The example given above means deep ocean (-1.01~0.29) will generate most frequently.\n\n");
			writer.write("### Vanilla\n\n");
			writer.write("```json\n{\n");
			writer.write("\t\"MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS\": -1.05,\n");
			writer.write("\t\"DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS\": -0.455,\n");
			writer.write("\t\"OCEAN_TO_COAST_CONTINENTALNESS\": -0.19,\n");
			writer.write("\t\"COAST_TO_INLAND_CONTINENTALNESS\": -0.11,\n");
			writer.write("\t\"NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS\": 0.03,\n");
			writer.write("\t\"MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS\": 0.3,\n");
			writer.write("\t\"PEAKS_CONTINENTALNESS\": 0.55,\n\n");
			writer.write("\t\"MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER\": -1.02,\n");
			writer.write("\t\"DEEP_OCEAN_TO_OCEAN_SHAPER\": -0.51,\n");
			writer.write("\t\"OCEAN_TO_COAST_SHAPER\": -0.44,\n");
			writer.write("\t\"COAST_WATER_SHAPER\": -0.18,\n");
			writer.write("\t\"COAST_BANK_SHAPER\": -0.16,\n");
			writer.write("\t\"COAST_LAND_SHAPER\": -0.15,\n");
			writer.write("\t\"NEAR_INLAND_SHAPER\": -0.1,\n");
			writer.write("\t\"INLAND_EROSION_SHAPER\": 0.06,\n");
			writer.write("\t\"MID_INLAND_SHAPER\": 0.25,\n");
			writer.write("\t\"PEAKS_EROSION_SHAPER\": 0.65,\n\n");
			writer.write("\t\"OCTAVE_CONTINENTALNESS_ADDER\": 0\n");
			writer.write("}\n```\n\n");
			writer.write("## Comments\n\n");
			writer.write("Here are some constructive comments for you to modify the shaper values:\n\n");
			writer.write("- MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER should be greater than MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS.\n");
			writer.write("- DEEP_OCEAN_TO_OCEAN_SHAPER should be a little less than DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS.\n");
			writer.write("- OCEAN_TO_COAST_SHAPER should be between DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS and OCEAN_TO_COAST_CONTINENTALNESS.\n");
			writer.write("- COAST_WATER_SHAPER should be close to OCEAN_TO_COAST_CONTINENTALNESS.\n");
			writer.write("- COAST_BANK_SHAPER should be between OCEAN_TO_COAST_CONTINENTALNESS and COAST_TO_INLAND_CONTINENTALNESS.\n");
			writer.write("- COAST_LAND_SHAPER should be a little greater than COAST_BANK_SHAPER.\n");
			writer.write("- NEAR_INLAND_SHAPER should be a little greater than COAST_TO_INLAND_CONTINENTALNESS.\n");
			writer.write("- INLAND_EROSION_SHAPER should be a little greater than NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS.\n");
			writer.write("- MID_INLAND_SHAPER should be a little less than MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS.\n");
			writer.write("- PEAKS_EROSION_SHAPER should be a little greater than PEAKS_CONTINENTALNESS.\n\n");
		}
	}

	private static void loadFromJson(JsonObject jsonObject) {
		CWGLogger.LOGGER.debug("Loading json config file.");
		IConfigValue.configValues.forEach(iConfigValue -> {
			if(jsonObject.has(iConfigValue.name())) {
				iConfigValue.parseAsValue(jsonObject.get(iConfigValue.name()));
			}
		});
	}

	private static void saveConfig() throws IOException {
		CWGLogger.LOGGER.debug("Saving json config file.");
		try(Writer writer = new FileWriter(configFile)) {
			JsonObject configJson = new JsonObject();
			IConfigValue.configValues.forEach(iConfigValue -> configJson.addProperty(iConfigValue.name(), iConfigValue.value()));
			IConfigHelper.writeJsonToFile(writer, null, configJson, 0);
		}
	}

	public static void checkValue(FloatConfigValue less, FloatConfigValue greater) {
		if(less.value() >= greater.value() - 1e-6F) {
			throw new ConfigValueException(less.name() + " is greater than " + greater.name() + "! Please check your config file.");
		}
	}

	public static void checkValues() {
		IConfigValue.configValues.forEach(IConfigValue::checkValueRange);

		checkValue(MUSHROOM_FIELDS_TO_DEEP_OCEAN_CONTINENTALNESS, DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS);
		checkValue(DEEP_OCEAN_TO_OCEAN_CONTINENTALNESS, OCEAN_TO_COAST_CONTINENTALNESS);
		checkValue(OCEAN_TO_COAST_CONTINENTALNESS, COAST_TO_INLAND_CONTINENTALNESS);
		checkValue(COAST_TO_INLAND_CONTINENTALNESS, NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS);
		checkValue(NEAR_INLAND_TO_MID_INLAND_CONTINENTALNESS, MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS);
		checkValue(MID_INLAND_TO_MOUNTAINS_CONTINENTALNESS, PEAKS_CONTINENTALNESS);

		checkValue(MUSHROOM_FIELDS_TO_DEEP_OCEAN_SHAPER, DEEP_OCEAN_TO_OCEAN_SHAPER);
		checkValue(DEEP_OCEAN_TO_OCEAN_SHAPER, OCEAN_TO_COAST_SHAPER);
		checkValue(OCEAN_TO_COAST_SHAPER, COAST_WATER_SHAPER);
		checkValue(COAST_WATER_SHAPER, COAST_BANK_SHAPER);
		checkValue(COAST_BANK_SHAPER, COAST_LAND_SHAPER);
		checkValue(COAST_LAND_SHAPER, NEAR_INLAND_SHAPER);
		checkValue(NEAR_INLAND_SHAPER, INLAND_EROSION_SHAPER);
		checkValue(INLAND_EROSION_SHAPER, MID_INLAND_SHAPER);
		checkValue(MID_INLAND_SHAPER, PEAKS_EROSION_SHAPER);
	}

	public static class ConfigValueException extends RuntimeException {
		public ConfigValueException(String message) {
			super(message);
		}
	}
}