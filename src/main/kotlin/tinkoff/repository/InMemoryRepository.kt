package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import java.util.concurrent.ConcurrentHashMap


@Service
class InMemoryRepository : TweetsRepository {

    private val tweets = ConcurrentHashMap<Long, Tweet>()

    override fun save(tweet: Tweet) {
        tweets[tweet.id] = tweet
    }

    override fun get(id: Long): Tweet? {
        return tweets[id]
    }
}