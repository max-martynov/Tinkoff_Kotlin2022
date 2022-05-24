package tinkoff.service.twitter

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientException
import org.springframework.web.reactive.function.client.WebClientResponseException
import tinkoff.model.Tweet
import tinkoff.model.TweetStatus

@Service
class TwitterClient(private val apiClient: APIClient) {

    suspend fun getLikesCount(tweetId: String): Int {
        var nextPage: String? = null
        var likesCount = 0
        while (true) {
            try {
                val likingUsers = apiClient.getPageOfLikingUsers(tweetId, nextPage)
                likesCount += likingUsers.meta.resultCount
                nextPage = likingUsers.meta.nextToken
                if (nextPage == null)
                    return likesCount
            } catch (e: WebClientException) {
                throw IllegalArgumentException(e.message)
            }
        }
    }

    suspend fun getTweet(tweetId: String): Tweet {
        try {
            val tweetInformation = apiClient.getTweetInformation(tweetId)
            return Tweet(
                tweetId,
                tweetInformation.data.text,
                tweetInformation.includes.users[0].username,
                TweetStatus.TRACKED
            )
        } catch (e: WebClientException) {
            throw IllegalArgumentException(e.message)
        }
    }

}
