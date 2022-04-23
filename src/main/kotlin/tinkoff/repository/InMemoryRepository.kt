package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Tweet

@Service
class InMemoryRepository : TweetsRepository {

    private val tweets = mutableMapOf<Long, Tweet>()

    override fun save(tweet: Tweet) {
        tweets[tweet.id] = tweet
    }

    override fun get(id: Long): Tweet? {
        return tweets[id]
    }
}