package tinkoff.repository.database

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository

@Primary
@Service
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

    override suspend fun clear(): Unit = newSuspendedTransaction {
        TweetTable.deleteAll()
    }
}