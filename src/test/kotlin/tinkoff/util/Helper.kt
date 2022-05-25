package tinkoff.util

import tinkoff.model.Tweet
import tinkoff.model.TweetStatus
import java.util.*

object Helper {

    fun createRandomTweet(
        id: String = UUID.randomUUID().toString(),
        text: String = UUID.randomUUID().toString(),
        authorUsername: String = UUID.randomUUID().toString(),
        status: TweetStatus = TweetStatus.TRACKED
    ) = Tweet(
        id,
        text,
        authorUsername,
        status
    )

}