package com.example.observer

import com.example.model.ATMPingStatus
import com.example.model.PingRequest
import com.example.plugins.AtmProperties
import com.example.service.AtmEventQueue
import com.example.util.DateTimeStamp
import kotlinx.coroutines.*
import kotlin.random.Random

class PingObserver(private val atmProperties: AtmProperties,
                   private val queue: AtmEventQueue) {

    private val scope = CoroutineScope( SupervisorJob() + Dispatchers.IO )

    fun start() {
        randomPingEvent()
        println("Ping event inits")
    }

    private fun  randomPingEvent() {
        scope.launch {
            while (scope.isActive) {
                delay(60000)
                println("Ping event generate event")
                scope.launch {
                    addPingEvent()
                }
            }
        }
    }

    private fun addPingEvent() {


       val event= PingRequest( bank = atmProperties.bank,
            atmid = atmProperties.id,
            status = if (Random.Default.nextDouble() < 0.9) ATMPingStatus.ON else ATMPingStatus.OFF,
            observed_at = DateTimeStamp.currentTime())
        queue.addEvent(event)
        println("Ping event added $event")


    }
}