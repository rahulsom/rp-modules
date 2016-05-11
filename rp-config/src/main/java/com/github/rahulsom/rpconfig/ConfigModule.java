package com.github.rahulsom.rpconfig;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages external configuration
 *
 * @author rahul somasunderam
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ConfigModule<T> extends AbstractModule {

    private final T configObject;

    public ConfigModule(T configObject) {
        this.configObject = configObject;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure() {
        try {
            bind(ConfigHandler.class).toInstance(new ConfigHandler<T>(configObject));
            bind(ConfigService.class).toInstance(new ConfigService<T>(configObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
