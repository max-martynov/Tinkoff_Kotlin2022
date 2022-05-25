package tinkoff

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import tinkoff.controller.RestController
import tinkoff.model.LikesRecord
import tinkoff.model.TweetResponse
import tinkoff.service.LikesCollector
import tinkoff.service.twitter.TwitterClient
import tinkoff.util.TwitterClientConfig
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestControllerTest(
    @Autowired private val restController: RestController,
    @Autowired private val likesCollector: LikesCollector
) {

    private val testClient = WebTestClient.bindToController(restController).build()

    @MockkBean
    private lateinit var twitterClient: TwitterClient

    private val twitterClientConfig = TwitterClientConfig()

    @BeforeEach
    fun `set up twitter client`() {
        twitterClientConfig.setUp(twitterClient)
    }

    @Nested
    inner class TrackTweet {

        @Test
        fun `simply start tracking tweet`() {
            val id = UUID.randomUUID().toString()
            startTrackingRequest(id)
                .expectStatus().isOk
                .expectBody<String>().isEqualTo("Request to track tweet with id=$id received.")
        }

    }

    @Nested
    inner class TrackAndGet {

        @Test
        fun `track tweet and get one record`() {
            val id = UUID.randomUUID().toString()
            startTrackingRequest(id)
            sleep(200L)
            val expected = TweetResponse(
                twitterClientConfig.defaultTweet(id),
                listOf(
                    LikesRecord(id, TwitterClientConfig.DEFAULT_LIKES_COUNT, LocalDateTime.now())
                )
            )
            val actual = getLikesRecordsRequest(id)
                .expectStatus().isOk
                .expectBody<TweetResponse>().returnResult()
            assertEquals(expected.tweet, actual.responseBody.tweet)
            assertEquals(expected.likesRecords[0].likesCount, actual.responseBody.likesRecords[0].likesCount)
        }

        @Test
        fun `track tweet and get multiple records`() {
            val id = UUID.randomUUID().toString()
            startTrackingRequest(id)
            sleep(100L)
            likesCollector.updateLikesRecords()
            sleep(100L)
            val expected = TweetResponse(
                twitterClientConfig.defaultTweet(id),
                listOf(
                    LikesRecord(id, TwitterClientConfig.DEFAULT_LIKES_COUNT, LocalDateTime.now()),
                    LikesRecord(id, TwitterClientConfig.DEFAULT_LIKES_COUNT, LocalDateTime.now()),
                )
            )
            val actual = getLikesRecordsRequest(id)
                .expectStatus().isOk
                .expectBody<TweetResponse>().returnResult()
            assertEquals(expected.tweet, actual.responseBody.tweet)
            expected.likesRecords.forEachIndexed { index, likesRecord ->
                assertEquals(likesRecord.likesCount, actual.responseBody.likesRecords[index].likesCount)
            }
        }
    }

    @Nested
    inner class Untrack {

        @Test
        fun `simply untrack tweet`() {
            val id = UUID.randomUUID().toString()
            startTrackingRequest(id)
            untrackRequest(id)
                .expectStatus().isOk
                .expectBody<String>().isEqualTo("Request to untrack tweet with id=$id received.")
        }

        @Test
        fun `not collecting likes of untracked tweet`() {
            val id = UUID.randomUUID().toString()
            startTrackingRequest(id)
            sleep(100L)
            untrackRequest(id)
            sleep(100L)
            likesCollector.updateLikesRecords()
            val tweetResponse = getLikesRecordsRequest(id)
                .expectStatus().isOk
                .expectBody<TweetResponse>().returnResult()
            assertEquals(1, tweetResponse.responseBody.likesRecords.size)
        }
    }

    private fun startTrackingRequest(id: String): WebTestClient.ResponseSpec =
        testClient.post()
            .uri {
                it.path("/track/tweet/$id")
                    .build()
            }
            .exchange()

    private fun getLikesRecordsRequest(id: String): WebTestClient.ResponseSpec =
        testClient.get()
            .uri {
                it.path("/likes/list/$id")
                    .build()
            }
            .exchange()

    private fun untrackRequest(id: String): WebTestClient.ResponseSpec =
        testClient.post()
            .uri {
                it.path("/untrack/tweet/$id")
                    .build()
            }
            .exchange()
}