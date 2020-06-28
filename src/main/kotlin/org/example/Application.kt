package org.example

import org.example.starter.server.GrizzlyWebServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Application(
    val grizzlyWebServer: GrizzlyWebServer
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        grizzlyWebServer.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    Thread.currentThread().join()
}