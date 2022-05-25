package tinkoff

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.EnableScheduling
import tinkoff.model.LikesRecordsRepository
import tinkoff.model.TweetStatus
import tinkoff.model.TweetsRepository
import tinkoff.service.LikesCollector
import tinkoff.service.twitter.TwitterClient
import tinkoff.util.Helper
import java.lang.Thread.sleep
import kotlin.test.assertEquals

@SpringBootTest
@EnableScheduling
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LikesCollectorTest(
    @Autowired private val tweetsRepository: TweetsRepository,
    @Autowired private val likesRecordsRepository: LikesRecordsRepository,
    @Autowired private val likesCollector: LikesCollector
) {

    @MockkBean
    lateinit var twitterClient: TwitterClient

    @BeforeEach
    fun `clear repositories`() {
        tweetsRepository.clear()
        likesRecordsRepository.clear()
        coEvery { twitterClient.getLikesCount(any()) } returns 10
    }

    @Nested
    inner class Tracked {

        @Test
        fun `LikesCollector collects records for single tracked tweet`() {
            val tweet = Helper.createRandomTweet(status = TweetStatus.TRACKED)
            val times = 3
            tweetsRepository.add(tweet)
            repeat(times) {
                likesCollector.updateLikesRecords()
            }
            sleep(500L)
            assertEquals(times, likesRecordsRepository.getRecords(tweet.id).size)
        }

        @Test
        fun `LikesCollector collects records for multiple tracked tweet`() {
            val tweets = List(3) {
                val tweet = Helper.createRandomTweet(status = TweetStatus.TRACKED)
                tweetsRepository.add(tweet)
                tweet
            }
            val times = 3
            repeat(times) {
                likesCollector.updateLikesRecords()
            }
            sleep(500L)
            tweets.forEach { tweet ->
                assertEquals(times, likesRecordsRepository.getRecords(tweet.id).size)
            }
        }
    }

    @Nested
    inner class Untracked {

        @Test
        fun `tweet with untracked status is ignored`() {
            val tweet = Helper.createRandomTweet(status = TweetStatus.UNTRACKED)
            val times = 3
            tweetsRepository.add(tweet)
            repeat(times) {
                likesCollector.updateLikesRecords()
            }
            sleep(500L)
            assertEquals(0, likesRecordsRepository.getRecords(tweet.id).size)
        }
    }
}