package com.example.routing.route

import com.example.model.Role
import com.example.routing.request.PingRequest
import com.example.routing.roleMatch
import com.example.service.PingService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.atmPingRoute(service : PingService) {

    
    authenticate("jwt-auth") {

        get("/{atmid}") {

            val principal: JWTPrincipal = call.principal()  ?: return@get call.respond(HttpStatusCode.Unauthorized)
            roleMatch(principal, Role.ADMIN) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val atmid = call.pathParameters.get("atmid") ?: return@get call.respond(HttpStatusCode.BadRequest)
            val pings=service.getPingByAtmId(atmid) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, pings)
        }

        post {
            val principal: JWTPrincipal = call.principal()  ?: return@post call.respond(HttpStatusCode.Unauthorized)
            roleMatch(principal,Role.ATM) ?: return@post call.respond(HttpStatusCode.Unauthorized)

            val pingRequest = call.receive<PingRequest>()
            val id =  service.addnewPing(pingRequest) ?: return@post call.respond(HttpStatusCode.BadRequest)
            call.respond(HttpStatusCode.OK,id)
        }

    }

}

