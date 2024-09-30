package config;

import static config.ConfigReader.getProperty;

public abstract class Properties {

    private Properties() {}
    public static final String BASE_URL = getProperty("base.url");
    public static final String API_VERSION = getProperty("api.version.endpoint");
}
