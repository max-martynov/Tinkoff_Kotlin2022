package tinkoff.repository.database

import org.jetbrains.exposed.sql.Table

object EventsTable : Table("EventsRepository") {
    val id = long("id")
    val type = varchar("type", 10)
    val body = varchar("body", 256)
    val status = varchar("status", 20)
}