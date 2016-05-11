package com.github.rahulsom.rpi18njson;

import com.google.inject.Singleton;
import ratpack.guice.ConfigurableModule;

/**
 * Manages i18n Bundles for SPAs by serving them as json
 */
public class MessageModule extends ConfigurableModule<MessageModule.Config> {
    public static class Config {
        private String bundleName = "messages";

        public String getBundleName() {
            return bundleName;
        }

        public Config bundleName(String bundleName) {
            this.bundleName = bundleName;
            return this;
        }
    }

    @Override
    protected void configure() {
        bind(MessageHandler.class).in(Singleton.class);
    }
}
