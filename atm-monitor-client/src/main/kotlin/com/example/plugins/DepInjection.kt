package com.example.plugins

import com.example.di.networkModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configDepInjection() {

    install(Koin) {
        slf4jLogger()
        modules(networkModule)
    }

}