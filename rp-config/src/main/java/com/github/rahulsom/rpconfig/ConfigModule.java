package com.github.rahulsom.rpconfig;

import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages external configuration
 *
 * @author rahul somasunderam
 */
@SuppressWarnings("WeakerAccess")
public class ConfigModule<T> extends AbstractModule {

    private Class<T> configClass;
    private String envPrefix;
    private String sysPrefix;

    @SuppressWarnings("unused")
    public ConfigModule(Class<T> configClass, String envPrefix, String sysPrefix) {
        this.configClass = configClass;
        this.envPrefix = envPrefix;
        this.sysPrefix = sysPrefix;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure() {
        try {
            final T configObject = ConfigHelper.getConfigObject(configClass, envPrefix, sysPrefix);
            bind(configClass).
                    toInstance(configObject);
            bind(ConfigHandler.class).
                    toInstance(new ConfigHandler<T>(configObject));
            bind(ConfigService.class).
                    toInstance(new ConfigService<T>(configObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
