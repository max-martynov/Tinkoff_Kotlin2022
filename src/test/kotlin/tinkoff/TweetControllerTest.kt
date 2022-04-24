package tinkoff

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.coEvery
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import tinkoff.controller.TweetController
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository
import tinkoff.service.TwitterClient
import java.time.Duration


@SpringBootTest
class TweetControllerTest(
    @Autowired private val tweetController: TweetController
) {

    private val testClient = WebTestClient.bindToController(tweetController).build()

    @MockkBean
    lateinit var twitterClient: TwitterClient

    @SpykBean
    lateinit var tweetsRepository: TweetsRepository

    companion object {
        private const val DEFAULT_TEXT = "default text"
        private const val DEFAULT_LIKES_COUNT = 123
    }

    @Nested
    inner class TestFunctionality {

        @BeforeEach
        fun setUp() {
            coEvery { twitterClient.getTweetText(any()) } returns DEFAULT_TEXT
            coEvery { twitterClient.getLikesCount(any()) } returns DEFAULT_LIKES_COUNT
            tweetsRepository.clear()
        }

        @Test
        fun `save and get tweet`(): Unit = runBlocking {
            val id = 1L
            startTrackingRequest(id)
                .expectStatus().isOk
                .expectBody<String>().isEqualTo("Tweet id=$id successfully received!")

            delay(500)
            val expected = Tweet(id, DEFAULT_TEXT, DEFAULT_LIKES_COUNT)

            getInfoRequest(id)
                .expectStatus().isOk
                .expectBody<Tweet>().isEqualTo(expected)
        }

        @Test
        fun `save and get a lot of tweets`(): Unit = runBlocking {
            val idsRange = (1L..100L)

            idsRange.forEach { id ->
                startTrackingRequest(id)
                    .expectStatus().isOk
                    .expectBody<String>().isEqualTo("Tweet id=$id successfully received!")
            }

            delay(1000)

            idsRange.forEach { id ->
                val expected = Tweet(id, DEFAULT_TEXT, DEFAULT_LIKES_COUNT)
                launch {
                    getInfoRequest(id)
                        .expectStatus().isOk
                        .expectBody<Tweet>().isEqualTo(expected)
                }
            }
        }
    }

    @Nested
    inner class TestEfficiency {

        @BeforeEach
        fun setUp() {
            coEvery { twitterClient.getTweetText(any()) } coAnswers {
                delay(10000L)
                DEFAULT_TEXT
            }
            coEvery { twitterClient.getLikesCount(any()) } coAnswers {
                delay(10000L)
                DEFAULT_LIKES_COUNT
            }
            tweetsRepository.clear()
        }

        @Test
        fun `start-tracking response returns without waiting for object creation`() {
            assertTimeout(Duration.ofMillis(1000L)) {
                runBlocking {
                    startTrackingRequest(1L)
                }
            }
        }

    }

    private suspend fun startTrackingRequest(id: Long): WebTestClient.ResponseSpec =
        testClient.post()
            .uri {
                it.path("/start-tracking")
                    .queryParam("id", id)
                    .build()
            }
            .exchange()

    private suspend fun getInfoRequest(id: Long): WebTestClient.ResponseSpec =
        testClient.get()
            .uri {
                it.path("/get-info/{id}")
                    .build(id)
            }
            .exchange()

}
