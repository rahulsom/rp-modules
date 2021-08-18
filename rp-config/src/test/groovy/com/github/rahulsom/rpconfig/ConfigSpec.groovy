package com.github.rahulsom.rpconfig

import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import ratpack.func.Action
import ratpack.guice.Guice
import ratpack.server.RatpackServerSpec
import ratpack.test.embed.EmbeddedApp
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.util.environment.RestoreSystemProperties

/**
 * Created by rahul on 3/19/16.
 */
class ConfigSpec extends Specification {
    static class AppConfig {
        String foo;
    }

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @AutoCleanup
    EmbeddedApp app = EmbeddedApp.of({ spec ->
        spec.registry(Guice.registry { b ->
                    b.module(new ConfigModule(ConfigHelper.getConfigObject(AppConfig, "APP_", "app.")))
                }).
                handlers { c ->
                    c.get("config", ConfigHandler)
                }
    } as Action<RatpackServerSpec>)

    void "should serve assets"() {
        given:
        def response = app.httpClient.get("/config")

        expect:
        response.statusCode == 200
        response.body.text.trim() == '{"foo":null}'
    }

    void "should serve assets when config set from env var"() {
        given:
        environmentVariables.set("APP_FOO", "something")
        def response = app.httpClient.get("/config")

        expect:
        response.statusCode == 200
        response.body.text.trim() == '{"foo":"something"}'
    }

    @RestoreSystemProperties
    void "should serve assets when config set from sysprop"() {
        given:
        System.setProperty("app.foo", "something-else")
        def response = app.httpClient.get("/config")

        expect:
        response.statusCode == 200
        response.body.text.trim() == '{"foo":"something-else"}'
    }

}
