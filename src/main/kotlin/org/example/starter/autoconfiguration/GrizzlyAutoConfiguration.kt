package org.example.starter.autoconfiguration

import org.example.starter.http.HttpServerFactory
import org.example.starter.server.GrizzlyWebServer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GrizzlyAutoConfiguration(
    val context: ApplicationContext
) {
    @Bean
    open fun httpServerFactory(): HttpServerFactory {
        return HttpServerFactory()
    }

    @Bean
    open fun grizzlyWebServer(): GrizzlyWebServer {
        return GrizzlyWebServer(httpServerFactory().create())
    }
}

