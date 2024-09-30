package config;

import exceptions.PropertyReaderException;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private ConfigReader() {
    }

    private static final String EXCEPTION_TEXT = "Error occurred during reading properties file: ";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Properties configs;

    public static String getProperty(final String propertyName) {
        synchronized (ConfigReader.class) {
            if (configs == null) {
                try {
                    var readerConfig = ConfigReader.class
                            .getClassLoader().getResourceAsStream(CONFIG_PROPERTIES);
                    final Properties properties = new Properties();
                    properties.load(readerConfig);
                    if (readerConfig != null) {
                        properties.load(readerConfig);
                    }
                    configs = properties;
                } catch (IOException ex) {
                    throw new PropertyReaderException(EXCEPTION_TEXT + ex.getMessage());
                }
            }
        }

        var systemProperty = System.getProperty(propertyName);
        var condition = configs.getProperty(propertyName, "Property is not found");
        return systemProperty == null ? condition : systemProperty;
    }
}
