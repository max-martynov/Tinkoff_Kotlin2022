package tinkoff.service.twitter

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriTemplate
import java.util.*


@Service
class APIClient(private val webClient: WebClient) {

    suspend fun getPageOfLikingUsers(tweetId: String, page: String?): LikingUsers {
        val uriTemplate = UriTemplate("/tweets/{tweetId}/liking_users")
        return webClient.get()
            .uri { uriBuilder -> uriBuilder
                .path(uriTemplate.expand(tweetId).path)
                .queryParamIfPresent("pagination_token", Optional.ofNullable(page))
                .build()
            }
            .retrieve()
            .awaitBody()
    }

    suspend fun getTweetInformation(tweetId: String): TweetInformation {
        val uriTemplate = UriTemplate("/tweets/{tweetId}")
        return webClient.get()
            .uri { uriBuilder -> uriBuilder
                .path(uriTemplate.expand(tweetId).path)
                .queryParam("expansions", "author_id")
                .build()
            }
            .retrieve()
            .awaitBody()
    }


}