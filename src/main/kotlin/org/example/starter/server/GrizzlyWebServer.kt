package org.example.starter.server

import org.glassfish.grizzly.http.server.HttpServer
import org.springframework.boot.web.server.WebServer
import org.springframework.boot.web.server.WebServerException
import java.io.Closeable
import java.util.concurrent.TimeUnit

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
        delegate.shutdown(3000, TimeUnit.MILLISECONDS)
    }

    override fun getPort(): Int {
        return delegate.getListener("grizzly").port;
    }

    override fun close() {
        stop()
    }
}