package com.example.repository

import com.example.model.Atmmedias
import com.example.model.Atmpings
import com.example.model.Atms
import com.example.routing.request.MediaRequest
import com.example.routing.response.MediaResponse
import com.example.util.DateTimeStamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

interface MediaRepository {

    fun findMediaByAtmId(id : String) : List<MediaResponse>
    fun addNewMedia(ping: MediaRequest) : Int?
}


class MediaRepositoryImp : MediaRepository {

    override fun findMediaByAtmId(id: String): List<MediaResponse> {
        return transaction {
            (Atms innerJoin  Atmmedias)
                .select(Atms.atmid, Atms.bank, Atmmedias.path, Atmmedias.observed_at, Atmmedias.created_at)
                .where( Atmmedias.atmId.eq(id)  and Atms.atmid.eq(id)  )
                .map (ResultRow::toMediaResponse)
        }
    }

    override fun addNewMedia(request: MediaRequest): Int? {
        return  transaction {
            Atmmedias.insert {
                it[atmId] = request.atmid
                it[path] = request.path
                it[observed_at] = DateTimeStamp.stamp(request.observed_at)
                it[created_at] = DateTimeStamp.stamp( DateTimeStamp.currentTime() )
            }.getOrNull(Atmmedias.id)

        }
    }

}

fun ResultRow.toMediaResponse() : MediaResponse {
   return MediaResponse(
        bank= this[Atms.bank],
        atmid =  this[Atms.atmid],
        path = this[Atmmedias.path],
        observed_at = this[Atmmedias.observed_at],
        created_at = this[Atmmedias.created_at]
    )
}