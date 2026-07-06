package dev.sillibeans.slowmine;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SlowMine implements ModInitializer {
    public static final String MOD_ID = "slow-mine";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static class Config {
        public float speed_peaceful = 1.3f;
        public float speed_easy = 1.3f;
        public float speed_normal = 1f;
        public float speed_hard = 0.6f;
    }

    public static final String CONFIG_FILENAME = MOD_ID + ".json";

    public static Config CONFIG = new Config();

    @Override
    @SuppressWarnings("all")
    public void onInitialize() {
        final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILENAME);
        Gson gson = new Gson();

        try {
            if (Files.exists(configPath)) {
                String jsonString = Files.readString(configPath);
                CONFIG = gson.fromJson(jsonString, Config.class);
            } else {
                Files.write(configPath, gson.toJson(CONFIG).getBytes());
            }

        } catch (IOException e) {
            LOGGER.error("Unknown exception occurred: ", e);
        }

        LOGGER.info("Hello from Slowmine!");
    }
}