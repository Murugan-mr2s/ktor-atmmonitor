package com.example.routing.request

import com.example.model.ATMPingStatus
import com.example.model.ATMServiceStatus
import com.example.model.ATMServiceType
import kotlinx.serialization.Serializable

@Serializable
data class BankAtmRequest(
    val bank: String,
    val atmid : String,
    val validity : Int,
)

@Serializable
data class PingRequest(
    val bank:String,
    val atmid : String,
    val status: ATMPingStatus,
    val observed_at : String)


@Serializable
data class MediaRequest(
    val bank:String,
    val atmid : String,
    val path : String,
    val observed_at : String)

@Serializable
data class ServiceRequest(
    val bank:String,
    val atmid : String,
    val user : String,
    val service : ATMServiceType,
    val status : ATMServiceStatus,
    val observed_at : String)

