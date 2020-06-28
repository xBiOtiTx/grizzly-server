package org.example

import org.example.dto.BaseMessage
import org.example.dto.LocationDto
import org.example.dto.MessageDto
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.socket.*
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient

@SpringBootTest
internal class ApplicationKtTest {
    private val URL = "ws://localhost:8080/echo"

    @org.junit.jupiter.api.Test
    fun main() {
        println("start!")

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

        for(i in 1..3) {
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