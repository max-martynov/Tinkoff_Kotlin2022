package tinkoff

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.reactive.function.BodyInserters
import tinkoff.controller.TweetController
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository
import tinkoff.service.TwitterClient
import java.lang.Thread.sleep

@SpringBootTest
class TweetTest(
    @Autowired private val tweetController: TweetController
//    @Autowired private val tweetsRepository: TweetsRepository
) {

    private val testClient = WebTestClient.bindToController(tweetController).build()

    @MockkBean
    lateinit var twitterClient: TwitterClient

    companion object {
        private const val DEFAULT_TEXT = "default text"
        private const val DEFAULT_LIKES_COUNT = 123
    }

    @BeforeEach
    fun setUp() {
        coEvery { twitterClient.getTweetText(any()) } returns DEFAULT_TEXT
        coEvery { twitterClient.getLikesCount(any()) } returns DEFAULT_LIKES_COUNT
    }

    @Test
    fun `save and get tweet`(): Unit = runBlocking {
        val id = 1L
        val job = launch {
            testClient.post()
                .uri {
                    it.path("/start-tracking")
                        .queryParam("id", id)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody<String>().isEqualTo("Tweet id=$id successfully received!")

        }
        delay(1000)
        launch {
            val expected = Tweet(id, DEFAULT_TEXT, DEFAULT_LIKES_COUNT)
            testClient.get()
                .uri {
                    it.path("/get-info/{id}")
                        .build(id)
                }
                .exchange()
                .expectStatus().isOk
                .expectBody<Tweet>().isEqualTo(expected)
        }
    }

}