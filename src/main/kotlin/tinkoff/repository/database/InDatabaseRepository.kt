package tinkoff.repository.database

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository

@Primary
@Service
class InDatabaseRepository : TweetsRepository {
    override fun save(tweet: Tweet): Unit = transaction {
        TweetTable.insert {
            it[tweetId] = tweet.id
            it[text] = tweet.text
            it[likesCount] = tweet.likeCount
        }
    }

    override fun get(id: Long): Tweet? = transaction {
        TweetTable.select { TweetTable.tweetId eq id }.singleOrNull()?.toTweet()
    }

    private fun ResultRow.toTweet() = Tweet(
        this[TweetTable.tweetId],
        this[TweetTable.text],
        this[TweetTable.likesCount]
    )
}