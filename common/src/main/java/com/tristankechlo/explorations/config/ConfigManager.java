package com.tristankechlo.explorations.config;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.platform.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public final class ConfigManager {

    private static final File CONFIG_DIR = Services.PLATFORM.getConfigDirectory().toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    public static final String FILE_NAME = Explorations.MOD_ID + ".json";
    private static final File CONFIG_FILE = new File(CONFIG_DIR, FILE_NAME);

    public static void loadAndVerifyConfig() {
        ConfigManager.createConfigFolder();

        if (!CONFIG_FILE.exists()) {
            ExplorationsConfig.setToDefault();
            ConfigManager.writeConfigToFile();
            Explorations.LOGGER.warn("No config '{}' was found, created a new one.", FILE_NAME);
            return;
        }

        try {
            ConfigManager.loadConfigFromFile();
            Explorations.LOGGER.info("Config '{}' was successfully loaded.", FILE_NAME);
        } catch (Exception e) {
            Explorations.LOGGER.error(e.getMessage());
            Explorations.LOGGER.error("Error loading config '{}', config hasn't been loaded. Using default config.", FILE_NAME);
            ExplorationsConfig.setToDefault();
        }
    }

    private static void loadConfigFromFile() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(CONFIG_FILE));
        JsonObject json = jsonElement.getAsJsonObject();
        ExplorationsConfig.fromJson(json);
    }

    private static void writeConfigToFile() {
        try {
            JsonElement jsonObject = ExplorationsConfig.toJson();
            JsonWriter writer = new JsonWriter(new FileWriter(CONFIG_FILE));
            writer.setIndent("\t");
            GSON.toJson(jsonObject, writer);
            writer.close();
        } catch (Exception e) {
            Explorations.LOGGER.error("There was an error writing the config to file: '{}'", FILE_NAME);
            Explorations.LOGGER.error(e.getMessage());
        }
    }

    private static void createConfigFolder() {
        if (!CONFIG_DIR.exists()) {
            if (!CONFIG_DIR.mkdirs()) {
                throw new RuntimeException("Could not create config folder: " + CONFIG_DIR.getAbsolutePath());
            }
        }
    }

}
