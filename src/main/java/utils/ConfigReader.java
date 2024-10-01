package utils;

import exceptions.CustomException;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final String EXCEPTION_TEXT = "Error occurred during reading properties file: ";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private ConfigReader() {
    }

    public static String getProperty(final String propertyName) {
        return PROPERTIES.getProperty(propertyName);
    }

    private static void loadProperties() {
        try {
            var readerConfig = ConfigReader.class
                    .getClassLoader().getResourceAsStream(CONFIG_PROPERTIES);
            if (readerConfig == null) {
                throw new CustomException("Properties not found");
            }
            PROPERTIES.load(readerConfig);
        } catch (IOException ex) {
            throw new CustomException(EXCEPTION_TEXT + ex.getMessage());
        }
    }
}
