package com.github.rahulsom.rpi18njson;

import com.google.inject.Inject;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.util.*;

/**
 * Created by rahul on 3/21/16.
 */
public class MessageHandler implements Handler {

    @Inject
    MessageModule.Config config;

    @Override
    public void handle(Context ctx) throws Exception {
        String acceptLanguageHeader = ctx.getRequest().getHeaders().get("Accept-Language");
        Map<String, String> retval = getBundleAsMap(null);

        final List<Locale.LanguageRange> locales =
                acceptLanguageHeader == null ?
                        new ArrayList<>() :
                        Locale.LanguageRange.parse(acceptLanguageHeader);

        locales.
                stream().
                sorted((o1, o2) -> o1.getWeight() - o2.getWeight() > 0 ? 1 : -1).
                map(Locale.LanguageRange::getRange).
                forEach(s -> retval.putAll(getBundleAsMap(s)));

        ctx.getResponse().contentType("application/json;charset=utf8");
        ctx.render(Jackson.json(retval));
    }

    Map<String, String> getBundleAsMap(String locale) {
        Properties prop = new Properties();
        String resourceName = locale == null ? config.getBundleName() : (config.getBundleName() + "_" + locale);
        InputStream ir = this.getClass().getClassLoader().getResourceAsStream(resourceName + ".properties");
        if (ir != null) {
            try {
                prop.load(new InputStreamReader(ir, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> retval = new HashMap<>();
        for (String k : prop.stringPropertyNames()) {
            retval.put(k, (String) prop.get(k));
        }
        return retval;
    }

}
