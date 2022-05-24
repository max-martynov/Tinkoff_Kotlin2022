package tinkoff.tweetsRepository

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.test.context.event.annotation.AfterTestClass
import tinkoff.model.Tweet
import tinkoff.model.TweetStatus
import tinkoff.model.TweetsRepository
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class TweetsRepositoryTest() {

    abstract val tweetsRepository: TweetsRepository

    @BeforeEach
    @AfterAll
    fun `clear repository`() {
        tweetsRepository.clear()
    }

    @Nested
    inner class AddAndGet {

        @Test
        fun `add single tweet`() {
            val tweet = Tweet("1", "2", "3", TweetStatus.TRACKED)
            tweetsRepository.add(tweet)
            assertEquals(tweet, tweetsRepository.getById(tweet.id))
        }

        @Test
        fun `add multiple tweets`() {
            repeat(10) {
                val tweet = Tweet("$it", "$it", "$it", TweetStatus.UNTRACKED)
                tweetsRepository.add(tweet)
                assertEquals(tweet, tweetsRepository.getById(tweet.id))
            }
        }
    }

    @Nested
    inner class GetAll {

        @Test
        fun `get all tweets`() {
            val tweets = List(5) {
                val tweet = Tweet("$it", "$it", "$it", TweetStatus.UNTRACKED)
                tweetsRepository.add(tweet)
                tweet
            }
            assertEquals(tweets.toSet(), tweetsRepository.getAll())
        }
    }

    @Nested
    inner class UpdateStatus {

        @Test
        fun `TRACKED to UNTRACKED and vice versa`() {
            val tweet = Tweet("1", "2", "3", TweetStatus.TRACKED)
            tweetsRepository.add(tweet)
            assertEquals(tweet, tweetsRepository.getById(tweet.id))
            tweetsRepository.updateStatus(tweet.id, TweetStatus.UNTRACKED)
            assertEquals(TweetStatus.UNTRACKED, tweetsRepository.getById(tweet.id)?.status)
            tweetsRepository.updateStatus(tweet.id, TweetStatus.TRACKED)
            assertEquals(tweet, tweetsRepository.getById(tweet.id))
        }

    }
}