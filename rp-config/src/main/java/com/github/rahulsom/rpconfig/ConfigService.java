package com.github.rahulsom.rpconfig;

import com.google.common.base.MoreObjects;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import ratpack.registry.Registry;
import ratpack.server.ServerConfig;
import ratpack.server.Service;
import ratpack.server.StartEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ConfigService<T> implements Service {

    private T config;

    ConfigService(T config) {
        this.config = config;
    }

    private final Logger log = LoggerFactory.getLogger(ConfigService.class);

    public void onStart(StartEvent event) throws Exception {
        log.info("OnStart info");
        final Registry registry = event.getRegistry();
        ServerConfig sc = registry.get(ServerConfig.class);
        if (sc.isDevelopment()) {
            log.info("Running in dev mode");
        } else {
            try {
                final InputStream bannerStream = this.getClass().getClassLoader().getResourceAsStream("banner.txt");
                final String banner = CharStreams.toString(new InputStreamReader(bannerStream));
                System.out.write(banner.getBytes());
            } catch (IOException ignore) {
                // The banner was not readable
            }
        }

        final Yaml yaml = new Yaml();
        log.error("Events: {}", yaml.dump(config));
    }

}
