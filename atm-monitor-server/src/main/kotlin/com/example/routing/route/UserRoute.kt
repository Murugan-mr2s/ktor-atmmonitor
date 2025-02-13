package com.example.routing.route

import com.example.model.Role
import com.example.routing.request.BankAtmRequest
import com.example.routing.request.UserRequest
import com.example.routing.response.AccessToken
import com.example.service.AtmService
import com.example.service.JwtService
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userRoute(service : UserService,
                    jwtService: JwtService,
                    atmService: AtmService) {

    authenticate("basic-auth") {

        get{
            //println(call.principal())
            val users = service.getAllUsers()
            call.respond(HttpStatusCode.OK,users)
        }


        post("user-token") {
           // println(call.principal())
            val request=call.receive<UserRequest>()
            val token = jwtService.generateToken(request) ?: return@post call.respond(HttpStatusCode.BadRequest)
            val name=service.addNewUser(request) ?: return@post call.respond(HttpStatusCode.BadRequest)
            call.respond(HttpStatusCode.Created,AccessToken(access_token = token))
        }


    }


    authenticate("jwt-auth") {

        post("atm-token") {
            var principal: JWTPrincipal? = call.principal()
            //principal?.audience?.equals(Role.ADMIN.name) ?: return@post call.respond(HttpStatusCode.BadRequest)
            val request=call.receive<UserRequest>()
            val token = jwtService.generateToken(request) ?: return@post call.respond(HttpStatusCode.BadRequest)
            val atmData=BankAtmRequest(bank = request.bank, atmid = request.name, validity = request.validity)
            atmService.addNewAtm(atmData) ?: return@post call.respond(HttpStatusCode.BadRequest)
            call.respond(HttpStatusCode.Created,AccessToken(access_token = token))
        }

    }




}