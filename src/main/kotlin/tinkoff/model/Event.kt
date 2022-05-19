package tinkoff.model

data class Event(
    val id: Long,
    val type: EventType,
    val body: String,
    val status: EventStatus
)
