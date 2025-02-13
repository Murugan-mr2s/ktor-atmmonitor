package com.example.repository

import com.example.model.Users
import com.example.routing.request.UserRequest
import com.example.routing.response.UserResponse
import com.example.util.DateTimeStamp
import io.ktor.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface UserRepository {

    fun findAll() : List<UserResponse>
    fun getUserByName(name :String) : UserResponse?
    fun addNewUser(user: UserRequest) : String?
}


class UserRepositoryImp : UserRepository {

    override fun findAll(): List<UserResponse> {
       return transaction {
           Users.selectAll().map(ResultRow::toUserResponse)
       }
    }

    override fun getUserByName(name: String): UserResponse? {
        return transaction {
            Users.selectAll()
                .where( Users.name.eq(name) )
                .map(ResultRow::toUserResponse).firstOrNull()
        }
    }

    override fun addNewUser(user: UserRequest): String? {

       return  transaction {

              Users.insert {
                it[name] = user.name
                it[organisation] = user.bank
                it[role] = user.role
                it[validity] = user.validity
                it[created_at] = DateTimeStamp.stamp( DateTimeStamp.currentTime() )
            }.getOrNull(Users.name)

        }
    }
}

fun ByteArray.toHexString(): String {
    return joinToString("") { "%02x".format(it) }
}

fun ResultRow.toUserResponse() : UserResponse {

    return  UserResponse(
          name = this[Users.name],
          role = this[Users.role],
          created_at = this[Users.created_at],
          bank = this[Users.organisation]
    )
}