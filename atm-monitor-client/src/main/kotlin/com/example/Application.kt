package com.example

import com.example.model.AtmModel
import com.example.observer.MediaObserver
import com.example.observer.PingObserver
import com.example.observer.ServiceObserver
import com.example.plugins.AtmProperties
import com.example.plugins.configDepInjection
import com.example.plugins.configSerialization
import com.example.service.AtmEventQueue
import com.example.service.WebHttpService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import java.util.LinkedList


fun main(array: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args = array)
}


fun Application.module() {


    configDepInjection()
    configSerialization()

    val prop : AtmProperties by inject { parametersOf(this) }
    val httpClient : HttpClient by inject()

    println(prop)
    val eventQueue : AtmEventQueue by inject { parametersOf(LinkedList<AtmModel>() )  }

    val webHttpService : WebHttpService by inject { parametersOf(httpClient,eventQueue) }
    webHttpService.start()

    val pingObserver : PingObserver by inject()
    pingObserver.start()
    val mediaObserver : MediaObserver by inject()
    mediaObserver.start()
    val serviceObserver : ServiceObserver by inject()
    serviceObserver.start()

}


