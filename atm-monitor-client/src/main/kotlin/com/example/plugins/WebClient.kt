package com.example.plugins

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.serializer


fun configHttp( atmProperties: AtmProperties): HttpClient {

    return HttpClient(CIO) {

        install(Logging){
            logger = Logger.DEFAULT
            level = LogLevel.INFO
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys=true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000 // 15 seconds
            connectTimeoutMillis = 5000 // 5 seconds
            socketTimeoutMillis = 15000 // 15 seconds
        }

        /*
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = "" , refreshToken = "")
                }
            }
        }*/

        defaultRequest {
            url { takeFrom(atmProperties.baseUrl) }
            header("Authorization","Bearer ${atmProperties.token}")
        }
        expectSuccess =true
    }

}