package com.example.model

import io.ktor.server.auth.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


enum class Role(name: String) {
    ADMIN("ADMIN"),USER("USER"), ATM("ATM")
}

data class User(
    val name :String,
    val password : String ,
    val role  :Role
)


object Users : Table() {
    val name = varchar("name",128)
    val organisation = varchar("organisation",128)
    val role  = enumeration("role", Role::class)
    val validity = integer("validity")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(name)
}



