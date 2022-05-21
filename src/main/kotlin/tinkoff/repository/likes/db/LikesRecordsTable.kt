package tinkoff.repository.likes.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object LikesRecordsTable : IntIdTable("LikesRecords") {
    val tweetId = varchar("tweet_id", 50)
    val likesCount = integer("likes_count")
    val relevantAt = datetime("relevant_at")
}