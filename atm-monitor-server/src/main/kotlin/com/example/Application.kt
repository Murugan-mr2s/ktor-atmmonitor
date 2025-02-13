package com.example

import com.example.plugins.configDatabase
import com.example.plugins.configDepInjection
import com.example.plugins.configSecurity
import com.example.plugins.configSerializer
import com.example.routing.configureRouting
import com.example.service.JwtService
import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

fun main(args : Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    configDepInjection()
    configSerializer()

    val jwtService : JwtService by inject { parametersOf(this)  }
    configSecurity(jwtService)
    configDatabase()
    configureRouting()

}