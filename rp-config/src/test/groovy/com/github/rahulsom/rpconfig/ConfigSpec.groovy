package com.github.rahulsom.rpconfig

import ratpack.func.Action
import ratpack.guice.Guice
import ratpack.server.RatpackServerSpec
import ratpack.test.embed.EmbeddedApp
import spock.lang.AutoCleanup
import spock.lang.Specification

/**
 * Created by rahul on 3/19/16.
 */
class ConfigSpec extends Specification {
    /*static class AppConfig {

    }
    @AutoCleanup
    @Delegate
    EmbeddedApp app = of({ spec ->
        spec.
                registry(Guice.registry { b ->
                    b.module(new ConfigModule(), {
                        it.configClass AppConfig
                        it.envPrefix 'APP_'
                        it.sysPrefix 'app.'
                    })
                }).

                handlers { c ->
                    c.get("config", ConfigHandler)
                }
    } as Action<RatpackServerSpec>)

    void "should serve assets"() {
        given:
        def response = httpClient.get("/config")

        expect:
        response.statusCode == 200
        response.body.text.trim() == BASE_DIR.resolve("../assets/html/index.html").text
    }*/
}
