package com.example.service

import com.example.model.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.netty.util.internal.ResourcesUtil
import kotlinx.coroutines.*
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File
import java.nio.file.Paths

class WebHttpService(private val httpClient: HttpClient,
                     private val queue: AtmEventQueue) : AtmEventObserver {

    private val scope = CoroutineScope( SupervisorJob() + Dispatchers.IO )

    fun start() {
        queue.addEventObserver(this)
    }

    private suspend fun sendEvent() {

        val atmModel = queue.getEvent()
        when(atmModel) {

            is MediaRequest -> {
                sendMedia(atmModel)
                println(atmModel)
            }

            is PingRequest -> {
                sendPing(atmModel)
                println(atmModel)
            }

            is ServiceRequest -> {
                sendSerivce(atmModel)
                println(atmModel)
            }
        }
    }

    override fun getNotification() {
        scope.launch {
            sendEvent()
        }
    }


    private suspend fun sendPing(pingRequest: PingRequest){
        httpClient.request {
            method = HttpMethod.Post
            url {
                appendPathSegments("pings")
            }
            contentType(ContentType.Application.Json)
            setBody(pingRequest)
        }
    }

    private suspend fun sendSerivce(serviceRequest: ServiceRequest){
        httpClient.request {
            method= HttpMethod.Post
            url {
                appendPathSegments("services")
            }
            contentType(ContentType.Application.Json)
            setBody(serviceRequest)
        }
    }

    private fun getResourcePath(fileName: String): String {
        val resourceUrl = javaClass.classLoader.getResource(fileName)
        return resourceUrl?.toURI()?.let { File(it).absolutePath }
            ?: throw IllegalArgumentException("Resource not found: $fileName")
    }

    private suspend fun sendMedia(mediaRequest: MediaRequest){

        val videofile =  Paths.get(getResourcePath(mediaRequest.path) ).normalize().toFile()

        httpClient.request {
            method = HttpMethod.Post
            url {
                appendPathSegments("medias")
            }

            setBody(MultiPartFormDataContent(
                formData {
                    append("media" , Json.encodeToString(serializer = serializer(MediaRequest::class.java) ,mediaRequest), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    })
                    append("image", videofile.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "video/mp4")
                        append(HttpHeaders.ContentDisposition, "filename=\"${mediaRequest.path}\"")
                    })
                },
                boundary = "WebAppBoundary"
            )
            )
            /*onUpload {
                    bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }*/
        }
    }
}