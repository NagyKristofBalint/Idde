package edu.bbte.idde.nkim2061.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Data
public class ConfigFactory {
    private static final ObjectMapper OBJECT_MAPPER;
    private static MainConfig mainConfig = new MainConfig();
    private static final String PROFILE_CHOOSER_FILE = "/application.json";

    static {
        OBJECT_MAPPER = new ObjectMapper();

        FileToRead fileToRead;

        try (InputStream initial_InputStream = ConfigFactory.class.getResourceAsStream(PROFILE_CHOOSER_FILE)) {
            fileToRead = OBJECT_MAPPER.readValue(initial_InputStream, FileToRead.class);
            log.info("Read following configuration: {}", fileToRead);
        } catch (IOException e) {
            log.error("Error choosing configuration", e);
            fileToRead = new FileToRead("dev");
        }

        if ("prod".equals(fileToRead.getProfile())) {
            try (InputStream inputStream =
                         ConfigFactory.class.getResourceAsStream("/application-prod.json")) {
                mainConfig = OBJECT_MAPPER.readValue(inputStream, MainConfig.class);
                mainConfig.setProfile("jdbc");
                log.info("Read following configuration: {}", mainConfig);
            } catch (IOException e) {
                mainConfig.setProfile("memory");
                log.error("Error loading jdbc config, switching to memory", e);
            }
        } else {
            mainConfig.setProfile("memory");
        }
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfig.getJdbcConfiguration();
    }

    public static String getProfile() {
        return mainConfig.getProfile();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FileToRead {
        @JsonProperty("profile")
        private String profile;
    }
}
