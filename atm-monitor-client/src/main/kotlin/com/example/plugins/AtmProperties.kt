package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.engine.*

class AtmProperties(private val application: Application) {

    val  getProperty ={ key:String ->
        application.environment.config.config("ktor.atm").property(key).getString()
    }

    val baseUrl= getProperty("baseurl")
    val token= getProperty("token")
    val bank = getProperty("bank")
    val id = getProperty("id")


}