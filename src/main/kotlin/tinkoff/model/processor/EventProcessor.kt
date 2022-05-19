package tinkoff.model.processor

import tinkoff.model.Event
import tinkoff.model.EventType

interface EventProcessor {
    fun process(event: Event): Boolean
}