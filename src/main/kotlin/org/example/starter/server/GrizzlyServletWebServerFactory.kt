package org.example.starter.server

import org.example.starter.http.HttpServerFactory
import org.springframework.boot.web.server.WebServer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.server.ServletWebServerFactory

class GrizzlyServletWebServerFactory(
    private val httpServerFactory: HttpServerFactory
) : ServletWebServerFactory {
    override fun getWebServer(vararg initializers: ServletContextInitializer): WebServer {
        return GrizzlyWebServer(httpServerFactory.create(*initializers))
    }
}