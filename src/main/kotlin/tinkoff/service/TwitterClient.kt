package tinkoff.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull


@Service
class TwitterClient(
    private val webClient: WebClient
) {

    suspend fun getTweetText(id: Long): String? =
        try {
            webClient.get().uri("/tweet/text/$id").retrieve().awaitBody()
        } catch (e: Exception) {
            null
        }

    suspend fun getLikesCount(id: Long): Int? =
        try {
            webClient.get().uri("/tweet/likes/$id").retrieve().awaitBody()
        } catch (e: Exception) {
            null
        }
}