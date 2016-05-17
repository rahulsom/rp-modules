package com.github.rahulsom.rpconfig;

import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.representer.Representer;
import ratpack.registry.Registry;
import ratpack.server.ServerConfig;
import ratpack.server.Service;
import ratpack.server.StartEvent;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

class ConfigService<T> implements Service {

    private T config;

    ConfigService(T config) {
        this.config = config;
    }

    private final Logger log = LoggerFactory.getLogger(ConfigService.class);

    public void onStart(StartEvent event) throws Exception {
        final Registry registry = event.getRegistry();
        ServerConfig sc = registry.get(ServerConfig.class);
        if (sc.isDevelopment()) {
            log.debug("Running in dev mode. No banner will be printed.");
        } else {
            try {
                final InputStream bannerStream = this.getClass().getClassLoader().getResourceAsStream("banner.txt");
                final String banner = CharStreams.toString(new InputStreamReader(bannerStream));
                System.out.write(banner.getBytes());
            } catch (IOException ignore) {
                // The banner was not readable
            }
        }

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        GroovyAwareRepresenter representer = new GroovyAwareRepresenter();
        final Yaml yaml = new Yaml(representer, dumperOptions);
        log.info("Configuration is\n{}", yaml.dump(config));
    }

    private static class GroovyAwareRepresenter extends Representer {
        @Override
        protected Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException {
            Set<Property> properties = super.getProperties(type);
            properties.removeIf(property -> property.getName().equals("metaClass"));
            return properties;
        }
    }
}
