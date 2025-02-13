package com.example.model

import kotlinx.serialization.Serializable

sealed interface AtmModel {
}

@Serializable
data class PingRequest(
    val bank:String,
    val atmid : String,
    val status: ATMPingStatus,
    val observed_at : String) :  AtmModel


@Serializable
data class MediaRequest(
    val bank:String,
    val atmid : String,
    val path : String,
    val observed_at : String) :  AtmModel

@Serializable
data class ServiceRequest(
    val bank:String,
    val atmid : String,
    val user : String,
    val service : ATMServiceType,
    val status : ATMServiceStatus,
    val observed_at : String) : AtmModel

