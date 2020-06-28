package org.example.starter.http

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.servlet.WebappContext
import org.springframework.boot.web.servlet.ServletContextInitializer

class HttpServerFactory(
    private val webappContext: WebappContext
) {
    fun create(vararg initializers: ServletContextInitializer): HttpServer {
        val httpServer: HttpServer = HttpServer.createSimpleServer()
        addServlets(*initializers)
        webappContext.deploy(httpServer)
        return httpServer
    }

    private fun addServlets(vararg initializers: ServletContextInitializer) {
        initializers.forEach {
            it.onStartup(webappContext)
        }
    }
}