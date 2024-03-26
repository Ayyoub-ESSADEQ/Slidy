package com.example.slidy
import kotlinx.serialization.Serializable

@Serializable
data class Content(val command : String, val attributes : String)

@Serializable
data class Message(val source: String,val destination: String, val content: Content)
