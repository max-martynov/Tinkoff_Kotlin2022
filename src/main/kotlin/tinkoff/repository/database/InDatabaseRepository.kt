package tinkoff.repository.database

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository
import java.sql.Connection.TRANSACTION_SERIALIZABLE

@Primary
@Repository
class InDatabaseRepository : TweetsRepository {
    override suspend fun save(tweet: Tweet): Unit = newSuspendedTransaction {
        TweetTable.insert {
            it[tweetId] = tweet.id
            it[text] = tweet.text
            it[likesCount] = tweet.likeCount
        }
    }

    override suspend fun get(id: Long): Tweet? = newSuspendedTransaction {
        TweetTable.select { TweetTable.tweetId eq id }.singleOrNull()?.toTweet()
    }

    private suspend fun ResultRow.toTweet() = Tweet(
        this[TweetTable.tweetId],
        this[TweetTable.text],
        this[TweetTable.likesCount]
    )

    override  fun clear(): Unit = transaction {
        TweetTable.deleteAll()
    }
}