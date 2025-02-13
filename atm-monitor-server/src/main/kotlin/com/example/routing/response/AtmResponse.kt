package com.example.routing.response
import com.example.model.ATMPingStatus
import com.example.model.ATMServiceStatus
import com.example.model.ATMServiceType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class BankAtmResponse(
    val bank: String,
    val atmid : String,
    val validity : Int,
    val created_at : LocalDateTime
)


@Serializable
data class PingResponse(
    val bank: String,
    val atmid : String,
    val status : ATMPingStatus,
    val observed_at : LocalDateTime,
    val created_at : LocalDateTime
)

@Serializable
data class MediaResponse(
    val bank: String,
    val atmid : String,
    val path : String,
    val observed_at : LocalDateTime,
    val created_at : LocalDateTime
)


@Serializable
data class ServiceResponse(
    val bank: String,
    val atmid : String,
    val user : String,
    val serive : ATMServiceType,
    val staus : ATMServiceStatus,
    val observed_at : LocalDateTime,
    val created_at : LocalDateTime
)
