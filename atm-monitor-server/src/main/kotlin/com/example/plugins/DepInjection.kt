package com.example.plugins

import com.example.di.repositorymodule
import com.example.di.serviceModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configDepInjection() {

    install(Koin) {
        slf4jLogger()
        modules(repositorymodule, serviceModule)
    }

}