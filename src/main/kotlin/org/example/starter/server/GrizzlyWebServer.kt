package org.example.starter.server

import org.glassfish.grizzly.http.server.HttpServer
import org.springframework.boot.web.server.WebServer
import org.springframework.boot.web.server.WebServerException
import java.io.Closeable

class GrizzlyWebServer(
    private val delegate: HttpServer
) : WebServer, Closeable {

    override fun start() {
        try {
            delegate.start()
        } catch (e: Exception) {
            throw WebServerException(e.message, e)
        }
    }

    override fun stop() {
        delegate.shutdownNow() // TODO gracefull params
    }

    override fun getPort(): Int {
        return delegate.getListener("grizzly").port;
    }

    override fun close() {
        stop()
    }
}