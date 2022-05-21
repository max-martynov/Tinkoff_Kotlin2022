package tinkoff.service.twitter

import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import tinkoff.model.TweetStatus

@Service
class TwitterClient(private val apiClient: APIClient) {

    suspend fun getLikesCount(tweetId: String): Int {
        var nextPage: String? = null
        var likesCount = 0
        while (true) {
            val likingUsers = apiClient.getPageOfLikingUsers(tweetId, nextPage)
            likesCount += likingUsers.meta.resultCount
            nextPage = likingUsers.meta.nextToken
            if (nextPage == null)
                return likesCount
        }
    }

    suspend fun getTweet(tweetId: String): Tweet {
        val tweetInformation = apiClient.getTweetInformation(tweetId)
        return Tweet(
            tweetId,
            tweetInformation.data.text,
            tweetInformation.includes.users[0].username,
            TweetStatus.TRACKED
        )
    }

}
