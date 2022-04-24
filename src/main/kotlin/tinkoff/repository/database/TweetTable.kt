package tinkoff.repository.database

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable

object TweetTable : IntIdTable("tweets") {
    val tweetId = long("tweet_id")
    val text = varchar("text", 280)
    val likesCount = integer("likes_count")
}