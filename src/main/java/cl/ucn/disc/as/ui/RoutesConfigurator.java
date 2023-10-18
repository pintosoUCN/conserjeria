/*
* Copyright (c) 2023. Arquitectura de Software
 */

package cl.ucn.disc.as.ui;
import io.javalin.Javalin;

/**
 * The Routes Config
 */
public interface RoutesConfigurator {
    /**
     * Configure routes
     * @param javalin to use
     */
    void configure(Javalin javalin);
}
