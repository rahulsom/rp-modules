package com.github.rahulsom.rpconfig;

import com.google.common.io.Resources;
import ratpack.config.ConfigData;
import ratpack.config.ConfigDataBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Created by rahul on 3/12/16.
 */
class ConfigHelper {

    static <T> T getConfigObject(Class<T> configClass, String envPrefix, String sysPrefix) throws Exception {
        final ConfigData configData = ConfigData.of(builder -> {
            ConfigHelper.loadExternalConfiguration(builder);
            builder.env(envPrefix);
            builder.sysProps(sysPrefix);
            builder.build();
        });
        return configData.get("/app", configClass);
    }

    static ConfigDataBuilder loadExternalConfiguration(final ConfigDataBuilder configDataBuilder) {

        final List<String> configurationLocations = Arrays.asList(
                "application.yml",
                "application.json",
                "application.properties",
                "config/application.yml",
                "config/application.json",
                "config/application.properties"
        );

        for (String configurationLocation : configurationLocations) {
            loadClasspathConfiguration(configDataBuilder, configurationLocation);
        }
        for (String configurationLocation : configurationLocations) {
            loadFileSystemConfiguration(configDataBuilder, configurationLocation);
        }

        return configDataBuilder;
    }

    private static void loadClasspathConfiguration(
            final ConfigDataBuilder configDataBuilder, final String configurationName) {

        try {
            final URL configurationResource = Resources.getResource(configurationName);

            if (yaml().apply(configurationName)) {
                configDataBuilder.yaml(configurationResource);
            } else if (json().apply(configurationName)) {
                configDataBuilder.json(configurationResource);
            } else if (properties().apply(configurationName)) {
                configDataBuilder.props(configurationResource);
            }

        } catch (IllegalArgumentException ignore) {
            // Configuration not found.
        }

    }

    private static void loadFileSystemConfiguration(
            final ConfigDataBuilder configDataBuilder, final String configurationFilename) {

        final Path configurationPath = Paths.get(configurationFilename);
        if (Files.exists(configurationPath)) {
            if (yaml().apply(configurationFilename)) {
                configDataBuilder.yaml(configurationPath);
            } else if (json().apply(configurationFilename)) {
                configDataBuilder.json(configurationPath);
            } else if (properties().apply(configurationFilename)) {
                configDataBuilder.props(configurationPath);
            }
        }
    }

    private static Function<String, Boolean> yaml() {
        return hasExtension("yml");
    }

    private static Function<String, Boolean> json() {
        return hasExtension("json");
    }

    private static Function<String, Boolean> properties() {
        return hasExtension("properties");
    }

    private static Function<String, Boolean> hasExtension(final String extension) {
        return s -> Pattern.compile(".*\\." + extension + "$").matcher(s).matches();
    }

}
