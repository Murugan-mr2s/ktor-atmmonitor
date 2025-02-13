package com.example.observer

import com.example.model.*
import com.example.plugins.AtmProperties
import com.example.service.AtmEventQueue
import com.example.util.DateTimeStamp
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random
import kotlin.time.Duration

class ServiceObserver(private val atmProperties: AtmProperties,
                      private val queue: AtmEventQueue) {

    private val scope = CoroutineScope( SupervisorJob() + Dispatchers.IO )

    fun start() {
        randomServiceEvent()
    }

    private fun  randomServiceEvent() {
      scope.launch {
          while (scope.isActive) {
              delay(80000)
              scope.launch {
                  addServiceEvent()
              }
          }
      }
    }

    private fun addServiceEvent() {
        val service =Math.ceil(Random.Default.nextDouble()*4)
        val serviceType = when( service.toInt() ) {
            1 -> ATMServiceType.DEPOSIT
            2 -> ATMServiceType.WITHDRAW
            3 -> ATMServiceType.BALANCE
            4 -> ATMServiceType.REPORT
            else -> ATMServiceType.BALANCE
        }

        val event= ServiceRequest( bank = atmProperties.bank,
            atmid = atmProperties.id,
            service = serviceType,
            status = if (Random.Default.nextDouble() < 0.9) ATMServiceStatus.SUCCESS else ATMServiceStatus.FAILURE,
            user = Math.ceil(Random.Default.nextDouble()*10).toInt().toString(),
            observed_at = DateTimeStamp.currentTime())
        queue.addEvent(event)

    }

}