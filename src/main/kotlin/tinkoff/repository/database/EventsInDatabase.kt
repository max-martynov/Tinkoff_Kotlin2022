package tinkoff.repository.database

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import tinkoff.model.Event
import tinkoff.model.EventStatus
import tinkoff.model.EventType
import tinkoff.repository.EventsRepository

@Repository
class EventsInDatabase : EventsRepository {
    override fun addEvent(event: Event): Unit = transaction {
        EventsTable.insert {
            it[id] = event.id
            it[type] = event.type.name
            it[body] = event.body
            it[status] = event.status.name
        }
    }

    override fun getEventsWithNewStatus(): List<Event> = transaction {
        EventsTable.select { EventsTable.status eq EventStatus.NEW.name }.map { it.toEvent() }
    }

    private fun ResultRow.toEvent() = Event(
        this[EventsTable.id],
        EventType.valueOf(this[EventsTable.type]),
        this[EventsTable.body],
        EventStatus.valueOf(this[EventsTable.status])
    )

    override fun updateStatus(eventId: Long, newStatus: EventStatus): Unit = transaction {
        EventsTable.update({ EventsTable.id eq eventId }) {
            it[status] = newStatus.name
        }
    }
}
