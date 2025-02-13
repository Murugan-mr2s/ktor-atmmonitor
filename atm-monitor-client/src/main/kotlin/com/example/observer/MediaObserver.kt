package com.example.observer

import com.example.model.MediaRequest
import com.example.plugins.AtmProperties
import com.example.service.AtmEventQueue
import com.example.util.DateTimeStamp
import kotlinx.coroutines.*
import kotlin.random.Random

class MediaObserver(private val atmProperties: AtmProperties,
                    private val queue: AtmEventQueue) {

    private val scope = CoroutineScope( SupervisorJob() + Dispatchers.IO )

    fun start() {
        randomPingEvent()
    }

    private fun  randomPingEvent() {
        scope.launch {
            while (scope.isActive) {
                delay(100000)
                scope.launch {
                    addMediaEvent()
                }
            }
        }
    }


   private fun addMediaEvent() {

       //random-video clip
       val id =Math.ceil(Random.Default.nextDouble()*4).toInt()
       val vclip= when(id) {
                   1 -> "v-clip$id.mp4"
                   2 -> "v-clip$id.mp4"
                   3 -> "v-clip$id.mp4"
                   4 -> "v-clip$id.mp4"
                   else -> "v-clip1.mp4"
                }

        val event=  MediaRequest ( bank = atmProperties.bank,
            atmid = atmProperties.id,
            path = "vsamples/".plus(vclip),
            observed_at = DateTimeStamp.currentTime())
        queue.addEvent(event)
    }

}