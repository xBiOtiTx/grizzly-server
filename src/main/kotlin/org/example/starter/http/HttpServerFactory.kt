package org.example.starter.http

import com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER
import org.glassfish.grizzly.http.server.CLStaticHttpHandler
import org.glassfish.grizzly.http.server.HttpHandlerRegistration
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.servlet.WebappContext
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.web.servlet.ServletContextInitializer
import java.io.IOException
import java.net.URISyntaxException
import java.util.function.Consumer
import javax.servlet.ServletException

class HttpServerFactory(
    private val webappContext: WebappContext
) {
    fun create(resourceConfig: ResourceConfig, vararg initializers: ServletContextInitializer): HttpServer {
//            HttpServer httpServer = aHttpServer()
//                .withScheme(properties.getHttp().getScheme())
//                .withHost(properties.getHttp().getHost())
//                .withPort(getProperty("server.port", String.valueOf(properties.getHttp().getPort())))
//                .withPath(properties.getHttp().getContextPath())
//                .withResourceConfig(resourceConfig)
//                .withCompressionMode(properties.getHttp().getCompressionMode())
//                .withCompressableMimeTypes(properties.getHttp().getCompressableMimeTypes())
//                .withCompressionMinSize(properties.getHttp().getMinimumCompressionSize().toBytes())
//                .build();

        val httpServer: HttpServer = HttpServer.createSimpleServer()
        addHttpHandler(httpServer)
        addServlets(*initializers)
        webappContext.deploy(httpServer)
        return httpServer
    }

    private fun addServlets(vararg initializers: ServletContextInitializer) {
//        initializers.forEach(onStartup(webappContext))
        initializers.forEach {
            it.onStartup(webappContext)
        }
    }

//    private fun onStartup(webappContext: WebappContext): Consumer<ServletContextInitializer> {
//        return Consumer<ServletContextInitializer> { initializer ->
//            try {
//                initializer.onStartup(webappContext)
//            } catch (e: ServletException) {
//                throw IllegalStateException(e)
//            } catch (e: UnsupportedOperationException) {
//                //LOGGER.warn("{}: {}", e.javaClass.name, e.message)
//            }
//        }
//    }

//    private fun addHttpHandler(httpServer: HttpServer) {
//        val httpHandler = CLStaticHttpHandler(javaClass.classLoader, properties.getHttp().getDocRoot())
//        httpHandler.isFileCacheEnabled = false // Disable cache because it's very very slow
//        for (urlMapping in properties.getHttp().getUrlMapping()) {
//            val mapping: HttpHandlerRegistration = builder().contextPath(properties.getHttp().getContextPath())
//                .urlPattern(urlMapping)
//                .build()
//            httpServer.getServerConfiguration()
//                .addHttpHandler(httpHandler, mapping)
//        }
//    }

    private fun addHttpHandler(httpServer: HttpServer) {
        val httpHandler = CLStaticHttpHandler(javaClass.classLoader, "/")
        httpHandler.isFileCacheEnabled = false // Disable cache because it's very very slow
        httpServer.serverConfiguration
            .addHttpHandler(httpHandler, HttpHandlerRegistration.ROOT)
    }
}