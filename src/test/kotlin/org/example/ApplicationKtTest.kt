package org.example

import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.*
import org.springframework.util.concurrent.SuccessCallback
import org.springframework.web.socket.*
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

internal class ApplicationKtTest {
    //    private val URL = "ws://localhost:8080/app/chat"
    private val URL = "ws://localhost:8080/echo"

    @org.junit.jupiter.api.Test
    fun main() {
        println("start!")
        start()

        val client: WebSocketClient = StandardWebSocketClient()

        val sessionFeature = client.doHandshake(object : WebSocketHandler {
            override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
                exception.printStackTrace()
            }

            override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
                TODO("Not yet implemented")
            }

            override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
                // TODO("Not yet implemented")
                val p = message.payload
                println("handleMessage $p")

            }

            override fun afterConnectionEstablished(session: WebSocketSession) {
                // TODO("Not yet implemented")
            }

            override fun supportsPartialMessages(): Boolean {
                return false
            }

        }, URL)

        val session = sessionFeature.get()
        session.sendMessage(TextMessage("Hello!"))

//            .doHandshake(LoggingWebSocketHandlerDecorator(object : WebSocketHandler {
//            }), URL)
//            .addCallback(SuccessCallback {  })

//        val stompClient = WebSocketStompClient(client)
//        stompClient.messageConverter = MappingJackson2MessageConverter()

//        val sessionHandler: StompSessionHandler = MyStompSessionHandler()
//        val future = stompClient.connect(URL, sessionHandler)
//        val session = future.get(5, TimeUnit.SECONDS) as StompSession
    }

    class MyStompSessionHandler : StompSessionHandlerAdapter() {
        override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
            println("afterConnected")
//            session.subscribe("/topic/messages", this)
//            session.send("/app/chat", sampleMessage)
        }

        override fun handleException(
            session: StompSession,
            command: StompCommand,
            headers: StompHeaders,
            payload: ByteArray,
            exception: Throwable
        ) {
            println("handleException")
        }

        override fun handleTransportError(session: StompSession, exception: Throwable) {
            println("handleTransportError")
            super.handleTransportError(session, exception)
        }

        override fun getPayloadType(headers: StompHeaders): Type {
            println("getPayloadType")
            return super.getPayloadType(headers)
        }

        override fun handleFrame(headers: StompHeaders, payload: Any) {
            println("handleFrame")
//            val msg: Message = payload as Message
            return super.handleFrame(headers, payload)
        }

        /**
         * A sample message instance.
         * @return instance of `Message`
         */
//        private val sampleMessage: Message
//            private get() {
//                val msg = Message()
//                msg.from = "Nicky"
//                msg.text = "Howdy!!"
//                return msg
//            }
    }
//
//    class Message {
//        var from: String? = null
//        var text: String? = null
//    }
}