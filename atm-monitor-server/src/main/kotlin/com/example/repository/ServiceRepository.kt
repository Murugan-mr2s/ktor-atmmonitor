package com.example.repository

import com.example.model.Atms
import com.example.model.Atmservices
import com.example.routing.request.ServiceRequest
import com.example.routing.response.ServiceResponse
import com.example.util.DateTimeStamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

interface ServiceRepository {
    fun findServiceByAtmId(id : String) : List<ServiceResponse>
    fun addNewService(request : ServiceRequest) : Int?
}

class ServiceRepositoryImp : ServiceRepository {
    override fun findServiceByAtmId(id: String): List<ServiceResponse> {

        return transaction {
            (Atms innerJoin  Atmservices)
                .select(Atms.atmid, Atms.bank, Atmservices.user, Atmservices.service, Atmservices.status, Atmservices.observed_at, Atmservices.created_at)
                .where( Atmservices.atmId.eq(id)  and Atms.atmid.eq(id)  )
                .map (ResultRow::toServiceResponse)
        }

    }

    override fun addNewService(request: ServiceRequest): Int? {
        return  transaction {
            Atmservices.insert {
                it[atmId] = request.atmid
                it[service] = request.service
                it[status] = request.status
                it[user] = request.user
                it[observed_at] = DateTimeStamp.stamp(request.observed_at)
                it[created_at] = DateTimeStamp.stamp( DateTimeStamp.currentTime() )
            }.getOrNull(Atmservices.id)

        }
    }


}


fun ResultRow.toServiceResponse() : ServiceResponse {
    return ServiceResponse(
        bank= this[Atms.bank],
        atmid =  this[Atms.atmid],
        user = this[Atmservices.user],
        serive = this[Atmservices.service],
        staus = this[Atmservices.status],
        observed_at = this[Atmservices.observed_at],
        created_at = this[Atmservices.created_at]
    )
}