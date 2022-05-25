package tinkoff.likesRecordsRepository

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import tinkoff.model.*
import tinkoff.util.Helper
import java.time.LocalDateTime
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class LikesRecordsRepositoryTest(
    private val tweetsRepository: TweetsRepository
) {

    abstract val likesRecordsRepository: LikesRecordsRepository

    @BeforeAll
    fun `clear tweet repository`() {
        tweetsRepository.clear()
    }

    @BeforeEach
    fun `clear likes records repository`() {
        likesRecordsRepository.clear()
    }


    @Nested
    inner class SingleRecord {

        @Test
        fun `one record for one tweet`() {
            val tweet = Helper.createRandomTweet()
            val likesRecord = LikesRecord(tweet.id, 10, LocalDateTime.now())
            likesRecordsRepository.addRecord(likesRecord)
            assertEquals(listOf(likesRecord), likesRecordsRepository.getRecords(tweet.id))
        }

        @Test
        fun `multiple records with one record`() {
            repeat(10) {
                val tweet = Helper.createRandomTweet()
                val likesRecord = LikesRecord(tweet.id, 10, LocalDateTime.now())
                likesRecordsRepository.addRecord(likesRecord)
                assertEquals(listOf(likesRecord), likesRecordsRepository.getRecords(tweet.id))
            }
        }
    }

    @Nested
    inner class MultipleRecords {

        @Test
        fun `for single tweet`() {
            val tweet = Helper.createRandomTweet()
            val records = List(5) {
                val likesRecord = LikesRecord(tweet.id, it, LocalDateTime.now())
                likesRecordsRepository.addRecord(likesRecord)
                likesRecord
            }
            assertContentEquals(records, likesRecordsRepository.getRecords(tweet.id))
        }
    }
}