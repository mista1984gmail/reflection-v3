package ru.clevertec.reflection.task.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import ru.clevertec.reflection.task.util.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LoadProperties {

    private static final Logger logger = LoggerFactory.getLogger(LoadProperties.class);
    public static Map<String, String> PROPERTIES = new HashMap<>();
    public static Yaml yaml = new Yaml();

    public static Map<String, String> getProperty() {
        if (PROPERTIES.size() == 0) {
            try (InputStream inputStream = new FileInputStream(Constants.PROPERTIES_FILE_NAME)) {
                PROPERTIES = yaml.load(inputStream);
            } catch (IOException ioe) {
                logger.error(ioe.getMessage());
            }
        }
        return PROPERTIES;
    }

}
