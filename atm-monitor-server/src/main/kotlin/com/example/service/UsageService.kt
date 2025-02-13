package com.example.service

import com.example.repository.AtmRepository
import com.example.repository.ServiceRepository
import com.example.routing.request.ServiceRequest
import com.example.routing.response.ServiceResponse

class UsageService(private val serviceRepository: ServiceRepository,
                   private val atmRepository: AtmRepository,) {


    fun getUsageByAtmId(atmid : String): List<ServiceResponse>? {
        val atmObj = atmRepository.findByAtmId(atmid) ?: return null
        return  serviceRepository.findServiceByAtmId(atmObj.atmid)
    }

    fun addnewUsage(request: ServiceRequest): Int? {
        val atmObj = atmRepository.findByAtmId(request.atmid) ?: return null
       return serviceRepository.addNewService(request)
    }

}