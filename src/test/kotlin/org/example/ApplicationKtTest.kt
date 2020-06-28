package org.example

import org.example.dto.BaseMessage
import org.example.dto.LocationDto
import org.example.dto.MessageDto
import org.glassfish.grizzly.Grizzly
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.socket.*
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient

@SpringBootTest
internal class ApplicationKtTest {
    private val LOGGER = Grizzly.logger(ApplicationKtTest::class.java)
    private val URL = "ws://localhost:8080/echo"

    val message = TextMessage(
        DtoMapper.MAPPER.writeValueAsString(
            BaseMessage(
                mapOf("TYPE" to "MESSAGE"),
                DtoMapper.MAPPER.writeValueAsBytes(MessageDto(LocationDto(0.0, 0.0), "Hello, server!(MESSAGE)"))
            )
        )
    )

    val location = TextMessage(
        DtoMapper.MAPPER.writeValueAsString(
            BaseMessage(
                mapOf("TYPE" to "LOCATION"),
                DtoMapper.MAPPER.writeValueAsBytes(LocationDto(0.0, 0.0))
            )
        )
    )

    @org.junit.jupiter.api.Test
    fun main() {
        val client: WebSocketClient = StandardWebSocketClient()

        val sessions = mutableListOf<WebSocketSession>()
        for (i in 1..100) {
            sessions.add(client.doHandshake(MyWebSocketHandler(), URL).get())
        }

        val messages = listOf(message, location)
        for (i in 1..10000) {
            LOGGER.info("sendMessage $i")
            val session = sessions.random()
            session.sendMessage(messages.random())
        }

        Thread.sleep(10000)
    }
}

class MyWebSocketHandler() : WebSocketHandler {
    private val LOGGER = Grizzly.logger(MyWebSocketHandler::class.java)

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        exception.printStackTrace()
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        TODO("Not yet implemented")
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        // TODO("Not yet implemented")
        val p = message.payload
        LOGGER.info("handleMessage $p")
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        // TODO("Not yet implemented")
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }
}