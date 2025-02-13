package com.example.service

import com.example.repository.AtmRepository
import com.example.repository.MediaRepository
import com.example.routing.request.MediaRequest
import com.example.routing.response.MediaResponse

class MediaService(private val mediaRepository: MediaRepository,
                   private val atmRepository: AtmRepository,) {


    fun getMediaByAtmId(atmid : String): List<MediaResponse>? {
        val atmObj = atmRepository.findByAtmId(atmid) ?: return null
        return mediaRepository.findMediaByAtmId(atmObj.atmid)
    }

    fun addNewMedia(request: MediaRequest): Int? {
        val atmObj = atmRepository.findByAtmId(request.atmid) ?: return null
        return mediaRepository.addNewMedia(request)
    }
}