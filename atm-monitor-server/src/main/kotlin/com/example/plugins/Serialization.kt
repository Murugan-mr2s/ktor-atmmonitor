package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*


fun Application.configSerializer() {

    install(ContentNegotiation) {
        json()
    }

}