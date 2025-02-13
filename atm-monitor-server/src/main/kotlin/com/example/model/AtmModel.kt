package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime



object Atms : Table() {
   // val id  = integer("id").autoIncrement()
   val atmid  = varchar("atmid", 128)
    val bank = varchar("bank",255)
    val validity = integer("validity")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(atmid)

}



object Atmpings : Table() {
    val id  = integer("id").autoIncrement()
    val atmId  = varchar("atmid",128).references(Atms.atmid)
    val status  = enumeration("status" , ATMPingStatus::class)
    val observed_at = datetime("observed_at")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(id)
}

object Atmmedias : Table() {
    val id  = integer("id").autoIncrement()
    val atmId  = varchar("atmid",128).references(Atms.atmid)
    val path  = varchar("path",255)
    val observed_at = datetime("observed_at")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(id)
}


object Atmservices : Table() {
    val id  = integer("id").autoIncrement()
    val atmId  = varchar("atmid",128).references(Atms.atmid)
    val user = varchar("user",64)
    val service = enumeration("service", ATMServiceType::class)
    val status  = enumeration("status" , ATMServiceStatus::class)
    val observed_at = datetime("observed_at")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(id)
}


