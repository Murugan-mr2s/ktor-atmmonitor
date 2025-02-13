package com.example.routing.route
import com.example.model.Role
import com.example.routing.request.MediaRequest
import com.example.routing.roleMatch
import com.example.service.MediaService
import com.example.service.MediaStorageService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.copyTo
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths


fun Route.atmMediaRoute(service: MediaService,
                        storageService: MediaStorageService) {


    authenticate("jwt-auth") {

        get("/{atmid}") {

            val principal: JWTPrincipal = call.principal()  ?: return@get call.respond(HttpStatusCode.Unauthorized)
            roleMatch(principal, Role.ADMIN) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val atmid = call.pathParameters.get("atmid") ?: return@get call.respond(HttpStatusCode.BadRequest)
            val medias=service.getMediaByAtmId(atmid) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, medias)
        }

        post {

            val principal: JWTPrincipal = call.principal()  ?: return@post call.respond(HttpStatusCode.Unauthorized)
            roleMatch(principal,Role.ATM) ?: return@post call.respond(HttpStatusCode.Unauthorized)

            val multiParts = call.receiveMultipart(formFieldLimit = 1024 * 1024 * 10)
            var requestTemp : MediaRequest?=null
            var fileBuffer : Buffer? =null
            var filename : String =""

            multiParts.forEachPart { part->

                when(part) {
                    is PartData.FormItem ->  {
                        if (part.name.equals("media") ) {
                            requestTemp = Json.decodeFromString<MediaRequest>(part.value)
                        }
                    }
                    is PartData.FileItem ->  {
                        filename = part.originalFileName as String
                        fileBuffer = part.provider().readBuffer()
                    }
                    else -> {}
                }
                part.dispose()
            }

            val mediaRequest = requestTemp ?: return@post call.respond(HttpStatusCode.BadRequest)
            val filePart  = fileBuffer ?: return@post call.respond(HttpStatusCode.BadRequest)
            val newfile=storageService.saveMedia(mediaRequest.atmid, filename, filePart) ?: return@post call.respond(HttpStatusCode.BadRequest)
            val id =  service.addNewMedia(mediaRequest.copy(path = newfile)) ?: return@post call.respond(HttpStatusCode.BadRequest)
            call.respond(HttpStatusCode.OK,id)

        }

        get("/{atmid}/{filename}") {

            val principal: JWTPrincipal = call.principal()  ?: return@get call.respond(HttpStatusCode.Unauthorized)
            roleMatch(principal,Role.ADMIN) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val atmid = call.pathParameters.get("atmid") ?: return@get call.respond(HttpStatusCode.BadRequest)
            val filename = call.pathParameters.get("filename") ?: return@get call.respond(HttpStatusCode.BadRequest)
            val mediaFile = storageService.downloadMedia(atmid,filename) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respondFile( mediaFile ) {
                headers {
                    append(HttpHeaders.ContentType, "video/mp4")
                    append(HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, mediaFile.name).toString())
                }
            }
        }


    }

}