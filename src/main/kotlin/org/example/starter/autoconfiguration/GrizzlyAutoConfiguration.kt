package org.example.starter.autoconfiguration

import org.example.starter.http.HttpServerFactory
import org.example.starter.server.GrizzlyServletWebServerFactory
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.servlet.WebappContext
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ConditionalOnClass(HttpServer::class)
//@AutoConfigureBefore(JerseyAutoConfiguration::class)
//@AutoConfigureAfter(ServletWebServerFactoryAutoConfiguration::class)
open class GrizzlyAutoConfiguration(
    val context: ApplicationContext
) {

    @Bean
    @ConditionalOnMissingBean
    open fun webappContext(properties: ServerProperties): WebappContext? {
//        LOGGER.info(
//            "Running with {} v{}",
//            GrizzlyAutoConfiguration::class.java.getPackage().specificationTitle,
//            GrizzlyAutoConfiguration::class.java.getPackage().specificationVersion
//        )
//        properties.servlet.applicationDisplayName,
//        properties.servlet.contextPath
        return object : WebappContext(
            "applicationDisplayName",
            "/contextPath"
        ) {
            override fun getClassLoader(): ClassLoader {
                return context.classLoader
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    open fun resourceConfig(): ResourceConfig {
        return ResourceConfig()
    }

    @Bean
    @ConditionalOnMissingBean
    open fun httpServerFactory(webappContext: WebappContext): HttpServerFactory {
        return HttpServerFactory(webappContext)
    }

    @Bean
    @ConditionalOnMissingBean
    open fun grizzlyServletWebServerFactory(
        resourceConfig: ResourceConfig,
        httpServerFactory: HttpServerFactory
    ): GrizzlyServletWebServerFactory {
        return GrizzlyServletWebServerFactory(resourceConfig.property("contextConfig", context), httpServerFactory)
    }

}

