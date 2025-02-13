package com.example.di

import com.example.model.AtmModel
import com.example.observer.MediaObserver
import com.example.observer.PingObserver
import com.example.observer.ServiceObserver
import com.example.plugins.AtmProperties
import com.example.plugins.configHttp
import com.example.service.AtmEventQueue
import com.example.service.WebHttpService
import io.ktor.client.*
import io.ktor.server.application.*
import org.koin.dsl.module
import java.util.LinkedList
import java.util.Queue


val networkModule = module {

    single<AtmProperties> { (application: Application) -> AtmProperties(application) }
    single<HttpClient> { configHttp(get()) }

    single<Queue<AtmModel>> { LinkedList() }
    single<AtmEventQueue> { AtmEventQueue(get() ) }
    single<PingObserver> { PingObserver(get(),get()) }
    single<MediaObserver> { MediaObserver(get(),get()) }
    single<ServiceObserver> { ServiceObserver(get(),get()) }
    single<WebHttpService> { WebHttpService(get(),get()) }

}