package tinkoff.repository

import tinkoff.model.Event
import tinkoff.model.EventStatus


interface EventsRepository {
    fun addEvent(event: Event)
    fun getEventsWithNewStatus(): List<Event>
    fun updateStatus(eventId: Long, newStatus: EventStatus)
    fun getEvent(id: Long): Event?
}
