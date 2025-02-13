package com.example.repository

import com.example.model.Atms
import com.example.routing.request.BankAtmRequest
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import com.example.routing.response.BankAtmResponse
import com.example.util.DateTimeStamp
import org.jetbrains.exposed.sql.insert

interface AtmRepository {

    fun findAll() : List<BankAtmResponse>
    fun findByAtmId(id : String) : BankAtmResponse?
    fun addNewAtm(atm: BankAtmRequest) : Int
}


class AtmRepositoryImp : AtmRepository {

    override fun findAll(): List<BankAtmResponse> {
        return transaction {
              Atms.selectAll().map(ResultRow::toBankAtm)
        }
    }

    override fun findByAtmId(id: String): BankAtmResponse? {

        return transaction {
            Atms.selectAll().where { Atms.atmid eq id }
                .map(ResultRow::toBankAtm)
                .singleOrNull()
        }
    }

    override fun addNewAtm(atm: BankAtmRequest): Int {

        return transaction {
            Atms.insert {
                it[bank] = atm.bank
                it[atmid] = atm.atmid
                it[validity] = atm.validity
                it[created_at] = DateTimeStamp.stamp( DateTimeStamp.currentTime() )
            }.insertedCount
        }
    }

}

fun ResultRow.toBankAtm() = BankAtmResponse(
                    bank = this[Atms.bank],
                    atmid = this[Atms.atmid],
                    validity = this[Atms.validity],
                    created_at = this[Atms.created_at])
