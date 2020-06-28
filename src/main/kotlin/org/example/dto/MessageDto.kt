package org.example.dto

import org.example.dto.LocationDto

data class MessageDto(
    val location: LocationDto,
    val text: String
)