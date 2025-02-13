package com.example.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {

        route("api/v1/clients") {

            get {
                call.respondText("response from atm clients")
            }
        }
    }
}