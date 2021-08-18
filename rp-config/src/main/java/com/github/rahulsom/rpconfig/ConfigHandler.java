package com.github.rahulsom.rpconfig;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

/**
 * Renders Configuration as JSON
 */
@SuppressWarnings("WeakerAccess")
public class ConfigHandler<T> implements Handler {

    private final T configObject;

    ConfigHandler(T configObject) {
        this.configObject = configObject;
    }

    @Override
    public void handle(Context ctx) {
        ctx.render(Jackson.json(configObject));
    }
}
