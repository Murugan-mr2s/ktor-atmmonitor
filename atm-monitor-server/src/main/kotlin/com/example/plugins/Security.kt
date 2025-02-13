package com.example.plugins


import com.example.service.JwtService
import com.example.model.User
import com.example.model.Role
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject


fun Application.configSecurity(jwtService : JwtService) {

   // val jwtService : JwtService by inject { parametersOf(this) }

    install(Authentication) {

        basic("basic-auth") {
            realm = "Access to 'basic'"
            validate { credentials ->

                val users = mapOf(
                    "admin" to  User("admin","admin", Role.ADMIN),
                    "user" to User("user","user", Role.USER)
                )


                val user = users[credentials.name]
                if (user!=null  && user.password == credentials.password) {
                        user //UserIdPrincipal(credentials.name)
                } else {
                        null
                }


            }
        }

        jwt("jwt-auth") {
             realm="atm-jwt-auth"
            verifier(jwtService.jwtVerifier)
            validate {
                    jwtCredential -> jwtService.UserValidator (jwtCredential)
            }
        }

    }




}