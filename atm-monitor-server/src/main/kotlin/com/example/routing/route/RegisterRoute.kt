package com.example.routing.route

import com.example.routing.request.BankAtmRequest
import com.example.service.AtmService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.atmRegisterRoute(service: AtmService) {

    get {
        call.respond(HttpStatusCode.OK, service.getAtms())
    }

    get("/{atmid}") {
        val atmid = call.pathParameters.get("atmid")  ?: return@get call.respond(HttpStatusCode.BadRequest)
        val response= service.getAtmById(atmid) ?: return@get call.respond(HttpStatusCode.BadRequest)
        call.respond(HttpStatusCode.OK,response)
    }

    post {
        val atmRequest = call.receive<BankAtmRequest>()
        val response=service.addNewAtm(atmRequest) ?: return@post call.respond(HttpStatusCode.BadRequest)
        call.respond(HttpStatusCode.Created,response)
    }


}