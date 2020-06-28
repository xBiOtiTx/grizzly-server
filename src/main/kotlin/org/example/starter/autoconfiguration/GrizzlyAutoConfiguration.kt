package org.example.starter.autoconfiguration

import org.example.starter.http.HttpServerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import javax.annotation.PostConstruct


@Configuration
open class GrizzlyAutoConfiguration(
    val context: ApplicationContext
) {
    @Bean
    open fun httpServerFactory(): HttpServerFactory {
        return HttpServerFactory()
    }

    @EventListener(ApplicationReadyEvent::class)
    open fun doSomethingAfterStartup() {
        println("doSomethingAfterStartup")
        HttpServerFactory().create().start()
        Thread.currentThread().join()
    }
}

