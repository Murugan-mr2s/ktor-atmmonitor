package com.example.di

import com.example.repository.*
import com.example.service.*
import io.ktor.server.application.*
import org.koin.dsl.module


val serviceModule = module {

    single { AtmService(get())  }
    single { PingService(get(),get())  }
    single { MediaService(get(),get())  }
    single {  UsageService(get(),get())  }
    single { MediaStorageService() }
    single { UserService(get()) }

    single<JwtService> { (application : Application) -> JwtService(application) }

}

