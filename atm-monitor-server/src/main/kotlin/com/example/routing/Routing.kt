package com.example.routing

import com.example.model.Role
import com.example.routing.route.*
import com.example.service.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject


fun Application.configureRouting() {

    val service : AtmService  by inject()
    val pingService : PingService by inject()
    val usageService : UsageService by inject()
    val mediaService : MediaService by inject()
    val storageService : MediaStorageService by inject()
    val userService : UserService by inject()
    val jwtService : JwtService by inject{ parametersOf(this) }

    routing {


        route("api/v1/users") {
            userRoute(service = userService, jwtService = jwtService, atmService = service)
        }

        route("api/v1/atms/register") {
            atmRegisterRoute(service = service)
        }

        route("api/v1/atms/pings") {
            atmPingRoute(service = pingService)
        }

        route("api/v1/atms/services") {
            atmServiceRoute(service = usageService)
        }

        route("api/v1/atms/medias") {
            atmMediaRoute(service = mediaService, storageService=storageService)
        }


    }

}

fun  roleMatch(principal: JWTPrincipal, role : Role) : Boolean? {
   val r= principal.payload.claims.get("role")?.asString() ?: return null
    return if ( r.equals(role.name) )  true  else null
}
