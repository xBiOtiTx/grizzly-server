package org.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.example.dto.BaseMessage
import org.example.dto.LocationDto
import org.example.dto.MessageDto
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
    private val URL = "ws://localhost:8080/echo"

    @org.junit.jupiter.api.Test
    fun main() {
        println("start!")
        start()

        val client: WebSocketClient = StandardWebSocketClient()

        val sessionFeature = client.doHandshake(MyWebSocketHandler(), URL)

        val session = sessionFeature.get()

        session.sendMessage(
            TextMessage(
                DtoMapper.MAPPER.writeValueAsString(
                    BaseMessage(
                        mapOf("TYPE" to "LOCATION"),
                        DtoMapper.MAPPER.writeValueAsBytes(LocationDto(0.0, 0.0))
                    )
                )
            )
        )

        session.sendMessage(
            TextMessage(
                DtoMapper.MAPPER.writeValueAsString(
                    BaseMessage(
                        mapOf("TYPE" to "MESSAGE"),
                        DtoMapper.MAPPER.writeValueAsBytes(MessageDto(LocationDto(0.0, 0.0), "Hello, World!"))
                    )
                )
            )
        )

        Thread.sleep(1000)

        for(i in 1..10) {
            Thread.sleep(1000)
            println("sendMessage $i")
            session.sendMessage(
                TextMessage(
                    DtoMapper.MAPPER.writeValueAsString(
                        BaseMessage(
                            mapOf("TYPE" to "MESSAGE"),
                            DtoMapper.MAPPER.writeValueAsBytes(MessageDto(LocationDto(0.0, 0.0), "Hello, server $i!"))
                        )
                    )
                )
            )
        }
    }
}

class MyWebSocketHandler() : WebSocketHandler {
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
}