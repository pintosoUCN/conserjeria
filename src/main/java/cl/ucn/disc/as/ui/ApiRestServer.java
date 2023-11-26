/*
 * Copyright (c) 2023. Arquitectura de Software, DISC, UCN.
 */

package cl.ucn.disc.as.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;

/**
 * The Server class.
 *
 * @author Arquitectura de Sistemas.
 */
@Slf4j
public final class ApiRestServer {

    /**
     * Nothing here
     */
    private ApiRestServer() {
        // nothing
    }

    /**
     * @return the configured Gson.
     */
    private static Gson createAndConfigureGson() {

        // Instant serializer
        TypeAdapter<Instant> instantTypeAdapter = new TypeAdapter<>() {

            /**
             * Instant to Long
             */
            @Override
            public void write(JsonWriter out, Instant instant) throws IOException {
                if (instant == null) {
                    out.nullValue();
                } else {
                    out.value(instant.toEpochMilli());
                }
            }

            /**
             * Long to Instant
             */
            @Override
            public Instant read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return Instant.ofEpochMilli(in.nextLong());
            }
        };

        // the Gson serializer
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, instantTypeAdapter)
                .create();

    }

    /**
     * Configure the server.
     *
     * @return the configured Javalin server.
     */
    private static Javalin createAndConfigureJavalin() {

        // gson config
        JsonMapper jsonMapper = new JsonMapper() {

            // the gson serializer
            private static final Gson GSON = createAndConfigureGson();

            /**
             * json to object
             */
            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                return GSON.fromJson(json, targetType);
            }

            /**
             * object to json
             */
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                return GSON.toJson(obj, type);
            }
        };

        // configure the server.
        return Javalin.create(config -> {

            // json mapper configuration
            config.jsonMapper(jsonMapper);

            // gzip compression
            config.compression.gzipOnly(9);
            // WARN: brotli need a native dll
            // config.compression.brotliAndGzip(9, 9);

            // enable logger
            config.requestLogger.http((ctx, ms) -> {
                log.debug("served: {} in {} ms.", ctx.fullUrl(), ms);
            });

            // enable debug logger
            config.plugins.enableDevLogging();
        });
    }

    /**
     * Starting the server.
     *
     * @param port to use.
     */
    public static Javalin start(final Integer port, final RoutesConfigurator routesConfigurator) {
        if (port < 1024 || port > 65535) {
            log.error("Bad port {}.", port);
            throw new IllegalArgumentException("Bad port: " + port);
        }
        log.debug("Starting api rest server in port {} ..", port);

        // the server
        Javalin app = createAndConfigureJavalin();

        // configure the paths
        routesConfigurator.configure(app);

        // the hookup thread
        Runtime.getRuntime()
                .addShutdownHook(new Thread(app::stop));

        // hooks to detect the shutdown
        app.events(event -> {
            event.serverStarting(() -> {
                log.debug("Starting the Javalin server ..");
            });
            event.serverStarted(() -> {
                log.debug("Server started!");
            });
            event.serverStopping(() -> {
                log.debug("Stopping the server ..");
            });
            event.serverStopped(() -> {
                log.debug("Server stopped!");
            });
        });


        // start!
        return app.start(port);
    }


}