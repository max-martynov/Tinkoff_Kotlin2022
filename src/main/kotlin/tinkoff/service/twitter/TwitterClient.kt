package tinkoff.service.twitter

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class TwitterClient(private val client: WebClient) {

    suspend fun getLikesCount(id: String): Int {
        var nextPage: String? = null
        var likesCount = 0
        while (true) {
            val response = executeRequest(id, nextPage)
            likesCount += response.meta.resultCount
            nextPage = response.meta.nextToken
            if (nextPage == null)
                return likesCount
        }
    }

    private suspend fun executeRequest(id: String, page: String?): LikingUsersResponse {
        val uri =
            if (page == null)
                "/tweets/$id/liking_users"
            else
                "/tweets/$id/liking_users?pagination_token=$page"
        return client.get()
            .uri(uri)
            .retrieve()
            .awaitBody()
    }

}

private data class LikingUsersResponse(
    val data: List<User>?,
    val meta: Meta
) {
    data class User(
        val id: String,
        val name: String,
        val username: String
    )

    data class Meta(
        @JsonProperty("result_count")
        val resultCount: Int,
        @JsonProperty("next_token")
        val nextToken: String?,
    )
}
