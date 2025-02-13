package com.example.plugins

import com.example.model.*

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configDatabase() {

    with(environment.config.config("ktor.postgres")) {

        Database.connect(url = property("url").getString(),
            user = property("user").getString(),
            password = property("password").getString())

        transaction {
               SchemaUtils.drop(Atms, Atmpings, Atmmedias, Atmservices, Users,  inBatch = true)
               SchemaUtils.create(Atms, Atmpings, Atmmedias, Atmservices,Users, inBatch = true)
        }


    }
}