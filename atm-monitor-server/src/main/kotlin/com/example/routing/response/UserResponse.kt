package com.example.routing.response

import com.example.model.Role
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    val name : String,
    val bank : String,
    val role : Role,
    val created_at : LocalDateTime
)

@Serializable
data class AccessToken(
    val access_token : String,
)
