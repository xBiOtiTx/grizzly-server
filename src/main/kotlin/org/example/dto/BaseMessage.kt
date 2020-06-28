package org.example.dto

class BaseMessage(
    val headers: Map<String, String>,
    val payload: ByteArray
)
