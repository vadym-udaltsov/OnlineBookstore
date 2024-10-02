package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigReader {
    private static final AppConfig config = ConfigFactory.create(AppConfig.class);
    private ConfigReader(){}
    public static String getBaseUrl() {
        return config.url();
    }
}
