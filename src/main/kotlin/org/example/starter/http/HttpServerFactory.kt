package org.example.starter.http

import org.example.ChatApplication
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.websockets.OptimizedBroadcaster
import org.glassfish.grizzly.websockets.WebSocketAddOn
import org.glassfish.grizzly.websockets.WebSocketApplication
import org.glassfish.grizzly.websockets.WebSocketEngine

class HttpServerFactory() {
    fun create(): HttpServer {
        val server: HttpServer = HttpServer.createSimpleServer()

        val addon = WebSocketAddOn()
        for (listener in server.listeners) {
            listener.registerAddOn(addon)
        }

        val chatApplication: WebSocketApplication = ChatApplication(OptimizedBroadcaster())
        WebSocketEngine.getEngine().register("", "/echo", chatApplication)

        return server
    }
}