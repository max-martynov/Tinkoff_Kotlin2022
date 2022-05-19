package tinkoff.model.processor

import tinkoff.model.Event
import tinkoff.model.EventType

object EmailProcessor : EventProcessor {

    override fun process(event: Event): Boolean {
        println("Processed successfully ${event.type} with body: \"${event.body}\"")
        return true
    }
}