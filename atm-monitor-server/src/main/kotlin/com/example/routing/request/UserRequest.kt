package com.example.routing.request

import com.example.model.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name : String,
    val bank : String,
    val role : Role,
    val validity: Int
)