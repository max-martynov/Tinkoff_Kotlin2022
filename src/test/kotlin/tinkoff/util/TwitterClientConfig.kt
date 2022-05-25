package tinkoff.util

import io.mockk.coEvery
import io.mockk.slot
import tinkoff.model.Tweet
import tinkoff.model.TweetStatus
import tinkoff.service.twitter.TwitterClient

class TwitterClientConfig {

    fun setUp(twitterClient: TwitterClient) {
        val id = slot<String>()
        coEvery { twitterClient.getTweet(capture(id)) } answers {
            Tweet(id.captured, DEFAULT_TEXT, DEFAULT_AUTHOR_USERNAME, DEFAULT_STATUS)
        }
        coEvery { twitterClient.getLikesCount(any()) } returns DEFAULT_LIKES_COUNT
    }

    fun defaultTweet(id: String) = Tweet(id, DEFAULT_TEXT, DEFAULT_AUTHOR_USERNAME, DEFAULT_STATUS)

    companion object {
        const val DEFAULT_TEXT = "This is super tweet!"
        const val DEFAULT_AUTHOR_USERNAME = "elonmusk"
        val DEFAULT_STATUS = TweetStatus.TRACKED
        const val DEFAULT_LIKES_COUNT = 10
    }

}