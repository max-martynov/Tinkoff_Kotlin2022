package tinkoff.repository

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import tinkoff.model.Tweet
import java.util.concurrent.ConcurrentHashMap

//@Primary
@Repository
class InMemoryRepository : TweetsRepository {

    private val tweets = ConcurrentHashMap<Long, Tweet>()

    override suspend fun save(tweet: Tweet) {
        tweets[tweet.id] = tweet
    }

    override suspend fun get(id: Long): Tweet? {
        return tweets[id]
    }

    override fun clear() {
        tweets.clear()
    }
}