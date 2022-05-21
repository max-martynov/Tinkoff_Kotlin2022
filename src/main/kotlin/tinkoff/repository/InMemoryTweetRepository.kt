package tinkoff.repository

import org.springframework.stereotype.Repository
import tinkoff.model.Tweet
import tinkoff.model.TweetRepository
import tinkoff.model.TweetStatus

@Repository
class InMemoryTweetRepository : TweetRepository {

    val tweets = mutableMapOf<String, Tweet>()

    override fun add(tweet: Tweet) {
        tweets[tweet.id] = tweet
    }

    override fun getById(id: String): Tweet? {
        return tweets[id]
    }

    override fun getAll(): Set<Tweet> {
        return tweets.values.toSet()
    }

    override fun updateStatus(id: String, newStatus: TweetStatus) {
        val tweet = tweets[id] ?: return
        val updated = Tweet(tweet.id, tweet.text, tweet.authorUsername, newStatus)
        tweets[id] = updated
    }

}