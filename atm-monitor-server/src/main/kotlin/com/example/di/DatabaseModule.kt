package com.example.di

import com.example.repository.*
import com.example.service.AtmService
import com.example.service.MediaService
import com.example.service.PingService
import com.example.service.UsageService
import org.koin.dsl.module


val repositorymodule = module {

    single<AtmRepository> { AtmRepositoryImp() }
    single<PingRepository> { PingRepositoryImp() }
    single<MediaRepository> { MediaRepositoryImp() }
    single<ServiceRepository> {  ServiceRepositoryImp() }
    single<UserRepository> {  UserRepositoryImp() }


    /*
     singleOf(::AtmRepositoryImp) { bind<AtmRepository>() }
     singleOf(::AtmService)
     */
}