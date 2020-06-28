package org.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class DtoMapper {
    companion object {
        val MAPPER = ObjectMapper()
            .registerModule(KotlinModule())
    }
}