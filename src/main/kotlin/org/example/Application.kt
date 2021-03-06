package org.example

import org.glassfish.grizzly.http.HttpRequestPacket
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.websockets.*


fun main() {
    println("Hello World!")

    start()

    println("Press any key to stop the server...")
    System.`in`.read()
}

fun start() {
//    val server: HttpServer = HttpServer.createSimpleServer("http://localhost", 8080)
    val server: HttpServer = HttpServer.createSimpleServer()
    val addon = WebSocketAddOn()
    for (listener in server.listeners) {
        listener.registerAddOn(addon)
    }


    val chatApplication: WebSocketApplication = ChatApplication(OptimizedBroadcaster())
    WebSocketEngine.getEngine().register("", "/echo", chatApplication)

    server.start()
}

class ChatApplication(
    val broadcaster: Broadcaster
) : WebSocketApplication() {
    val sockets = mutableListOf<WebSocket>()

    override fun createSocket(
        handler: ProtocolHandler,
        requestPacket: HttpRequestPacket,
        vararg listeners: WebSocketListener
    ): WebSocket {
        println("createSocket")
        val socket = super.createSocket(
            handler,
            requestPacket,
            *listeners
        ) as DefaultWebSocket
//        socket.setBroadcaster(broadcaster)

        sockets.add(socket)

        return socket
    }

    override fun onMessage(socket: WebSocket?, bytes: ByteArray?) {
        println("onMessage")
        super.onMessage(socket, bytes)
    }

    override fun onPong(socket: WebSocket?, bytes: ByteArray) {
        println("onPong: " + bytes.toString(Charsets.UTF_8))
        super.onPong(socket, bytes)
    }

    override fun onPing(socket: WebSocket?, bytes: ByteArray?) {
        println("onPing")
        super.onPing(socket, bytes)
    }

    override fun onFragment(socket: WebSocket?, fragment: String?, last: Boolean) {
        println("onFragment")
        super.onFragment(socket, fragment, last)
    }

    override fun onFragment(socket: WebSocket?, fragment: ByteArray?, last: Boolean) {
        println("onFragment")
        super.onFragment(socket, fragment, last)
    }

    override fun onMessage(socket: WebSocket?, text: String?) {
        println("onMessage: $text")
        super.onMessage(socket, text)
    }

    override fun onConnect(socket: WebSocket) {
        println("onConnect")
        // socket.sendPing("server sendPing".toByteArray(Charsets.UTF_8))
        socket.send("CONNECTED")
        super.onConnect(socket)
    }

    override fun onClose(socket: WebSocket?, frame: DataFrame?) {
        println("onClose")
        super.onClose(socket, frame)
    }

    override fun onError(webSocket: WebSocket?, t: Throwable?): Boolean {
        println("onError")
        return super.onError(webSocket, t)
    }

//    override fun isApplicationRequest(request: HttpRequestPacket?): Boolean {
//        return super.isApplicationRequest(request)
//    }
}

//class ChatWebSocket(
//    private val app: ChatApplication,
//    request: Request?,
//    response: Response?
//) : BaseServerWebSocket(app, request, response) {
//    var user: String? = null
//
//    fun send(data: String) {
//        super.send(toJsonp(user, data))
//    }
//
//    @Throws(IOException::class)
//    fun close() {
//        WebSocketsServlet.logger.info("closing : $user")
//        app.remove(this)
//        super.close()
//    }
//
//    private fun toJsonp(name: String?, message: String): String {
//        return """window.parent.app.update({ name: "${escape(name)}", message: "${escape(message)}" });
//"""
//    }
//
//    private fun escape(orig: String?): String {
//        val buffer = StringBuilder(orig!!.length)
//        for (i in 0 until orig.length) {
//            val c = orig[i]
//            when (c) {
//                '\b' -> buffer.append("\\b")
//                '\f' -> buffer.append("\\f")
//                '\n' -> buffer.append("")
//                '\r' -> {
//                }
//                '\t' -> buffer.append("\\t")
//                '\'' -> buffer.append("\\'")
//                '\"' -> buffer.append("\\\"")
//                '\\' -> buffer.append("\\\\")
//                '<' -> buffer.append("<")
//                '>' -> buffer.append(">")
//                '&' -> buffer.append("&")
//                else -> buffer.append(c)
//            }
//        }
//        return buffer.toString()
//    }
//}