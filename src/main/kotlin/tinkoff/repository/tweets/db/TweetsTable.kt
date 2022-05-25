package tinkoff.repository.tweets.db

import org.jetbrains.exposed.sql.Table

object TweetsTable : Table("Tweets") {
    val id = varchar("id", 50)
    val text = varchar("text", 280)
    val authorUsername = varchar("author_username", 50)
    val status = varchar("status", 20)
}