package org.example.starter.server

import org.example.starter.http.HttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.web.server.WebServer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.server.ServletWebServerFactory

class GrizzlyServletWebServerFactory(
    private val resourceConfig: ResourceConfig,
    private val httpServerFactory: HttpServerFactory
) : ServletWebServerFactory {
    override fun getWebServer(vararg initializers: ServletContextInitializer): WebServer {
        return GrizzlyWebServer(httpServerFactory.create(resourceConfig, *initializers))
    }
}