package org.example

import org.example.dto.BaseMessage
import org.example.dto.LocationDto
import org.example.dto.MessageDto
import org.glassfish.grizzly.http.HttpRequestPacket
import org.glassfish.grizzly.websockets.*

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

    override fun onMessage(socket: WebSocket, text: String?) {
        println("onMessage: $text")
        val message = DtoMapper.MAPPER.readValue(text, BaseMessage::class.java)
        val type = message.headers["TYPE"]
        when (type) {
            "LOCATION" -> {
                println("on LOCATION")
                val dto = DtoMapper.MAPPER.readValue(message.payload, LocationDto::class.java)
            }
            "MESSAGE" -> {
                println("on MESSAGE")
                val dto = DtoMapper.MAPPER.readValue(message.payload, MessageDto::class.java)
                Thread.sleep(3000)
                socket.send(DtoMapper.MAPPER.writeValueAsString(
                    BaseMessage(
                        mapOf("TYPE" to "MESSAGE"),
                        DtoMapper.MAPPER.writeValueAsBytes(MessageDto(LocationDto(0.0, 0.0), "Hello, client!"))
                    )
                ))
            }
        }
    }

    override fun onConnect(socket: WebSocket) {
        println("onConnect")
        // socket.sendPing("server sendPing".toByteArray(Charsets.UTF_8))
        // socket.send("CONNECTED")
        super.onConnect(socket)
    }

    override fun onClose(socket: WebSocket?, frame: DataFrame?) {
        println("onClose")
        super.onClose(socket, frame)
    }

    override fun onError(webSocket: WebSocket?, t: Throwable?): Boolean {
        println("onError")
        t?.printStackTrace()
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