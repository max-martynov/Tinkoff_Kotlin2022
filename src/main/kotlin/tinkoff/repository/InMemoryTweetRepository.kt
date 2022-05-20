package tinkoff.repository

import org.springframework.stereotype.Repository
import tinkoff.model.LikesTrack
import tinkoff.model.Tweet
import tinkoff.model.TweetRepository
import tinkoff.model.TweetStatus

@Repository
class InMemoryTweetRepository : TweetRepository {
    override fun add(tweet: Tweet) {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): Tweet? {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Tweet> {
        TODO("Not yet implemented")
    }

    override fun updateStatus(id: String, newStatus: TweetStatus) {
        TODO("Not yet implemented")
    }

    override fun updateLikesTrack(id: String, newLikesTrack: LikesTrack) {
        TODO("Not yet implemented")
    }
}