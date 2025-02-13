package com.example.repository

import com.example.model.Atmpings
import com.example.model.Atms
import com.example.routing.request.PingRequest
import com.example.routing.response.PingResponse
import com.example.util.DateTimeStamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

interface PingRepository {
    fun findPingByAtmId(id : String) : List<PingResponse>
    fun addNewPing(ping:PingRequest) : Int?
}

class PingRepositoryImp : PingRepository {

    override fun findPingByAtmId(id: String): List<PingResponse> {
       return transaction {
            (Atms innerJoin  Atmpings)
                .select(Atms.atmid,Atms.bank, Atmpings.status, Atmpings.observed_at,Atmpings.created_at)
                .where( Atmpings.atmId.eq(id)  and Atms.atmid.eq(id)  )
                .map (ResultRow::toPingResponse)
        }
    }

    override fun addNewPing(request: PingRequest): Int? {

      return  transaction {
            Atmpings.insert {
                it[atmId] = request.atmid
                it[status] = request.status
                it[observed_at] = DateTimeStamp.stamp(request.observed_at)
                it[created_at] = DateTimeStamp.stamp( DateTimeStamp.currentTime() )
            }.getOrNull(Atmpings.id)

        }
    }
}

fun ResultRow.toPingResponse() : PingResponse {
  return  PingResponse(
        bank= this[Atms.bank],
        atmid =  this[Atms.atmid],
        status = this[Atmpings.status],
        observed_at = this[Atmpings.observed_at],
        created_at = this[Atmpings.created_at]
        )
}