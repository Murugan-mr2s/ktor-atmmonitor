package com.example.service

import com.example.model.AtmModel
import java.util.LinkedList

interface  AtmEventInf {
    fun addEvent(event: AtmModel)
    fun getEvent() : AtmModel
    fun hasEvent() : Boolean
    fun addEventObserver(observer: AtmEventObserver)
}

interface  AtmEventObserver {
    fun getNotification()
}


class AtmEventQueue(private val queue : LinkedList<AtmModel>) : AtmEventInf {

    private  val observerList = mutableListOf<AtmEventObserver>()

    override fun addEvent(event : AtmModel) {
        queue.offer(event)
        observerList.forEach { observer->
            observer.getNotification()
        }
    }

    override fun hasEvent()  = queue.isEmpty()

    override fun getEvent() : AtmModel {
        return queue.poll()
    }

    override fun addEventObserver(observer: AtmEventObserver) {
        observerList.add(observer)
    }
}
