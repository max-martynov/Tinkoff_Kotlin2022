package tinkoff.repository.tweets.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import tinkoff.model.Tweet
import tinkoff.model.TweetStatus
import tinkoff.model.TweetsRepository

@Primary
@Repository
class DBTweetsRepository : TweetsRepository {
    override fun add(tweet: Tweet): Unit = transaction {
        TweetsTable.insert {
            it[id] = tweet.id
            it[text] = tweet.text
            it[authorUsername] = tweet.authorUsername
            it[status] = tweet.status.name
        }
    }

    override fun getById(id: String): Tweet? = transaction {
        TweetsTable.select { TweetsTable.id eq id }.firstOrNull()?.toTweet()
    }

    private fun ResultRow.toTweet() = Tweet(
        this[TweetsTable.id],
        this[TweetsTable.text],
        this[TweetsTable.authorUsername],
        TweetStatus.valueOf(this[TweetsTable.status])
    )

    override fun getAll(): Set<Tweet> = transaction {
        TweetsTable.selectAll().map { it.toTweet() }.toSet()
    }

    override fun updateStatus(id: String, newStatus: TweetStatus): Unit = transaction {
        TweetsTable.update({ TweetsTable.id eq id }) {
            it[status] = newStatus.name
        }
    }
}