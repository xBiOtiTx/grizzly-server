package org.example.starter.http

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.servlet.WebappContext
import org.springframework.boot.web.servlet.ServletContextInitializer

class HttpServerFactory() {
    fun create(): HttpServer {
        val httpServer: HttpServer = HttpServer.createSimpleServer()
        return httpServer
    }
}