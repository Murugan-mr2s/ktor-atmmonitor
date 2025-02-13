package com.example.service

import com.example.repository.AtmRepository
import com.example.routing.request.BankAtmRequest
import com.example.routing.response.BankAtmResponse

class AtmService(private val repository : AtmRepository) {

    fun getAtms(): List<BankAtmResponse> {
        return repository.findAll()
    }

    fun getAtmById(id : String): BankAtmResponse? {
       return repository.findByAtmId(id)
    }

    fun addNewAtm(atm : BankAtmRequest) : Int? {
        return repository.addNewAtm(atm)
    }


}