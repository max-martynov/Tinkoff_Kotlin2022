package tinkoff.model

data class ReceivedEvent(
    val id: Long,
    val type: EventType,
    val body: String,
) {
    fun toEvent() = Event(id, type, body, EventStatus.NEW)
}
