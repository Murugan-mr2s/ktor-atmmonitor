package com.example.service

import com.example.repository.AtmRepository
import com.example.repository.PingRepository
import com.example.routing.request.PingRequest
import com.example.routing.response.PingResponse
import com.example.util.DateTimeStamp


class PingService(private val pingRepository: PingRepository,
                  private val atmRepository: AtmRepository,) {


    fun getPingByAtmId(atmid : String): List<PingResponse>? {
        val atmObj = atmRepository.findByAtmId(atmid) ?: return null
        return pingRepository.findPingByAtmId(atmObj.atmid)
    }

    fun addnewPing(request: PingRequest): Int? {
        val atmObj = atmRepository.findByAtmId(request.atmid) ?: return null
        return pingRepository.addNewPing(request)
    }

}
