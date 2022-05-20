package tinkoff.service

import kotlinx.coroutines.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordRepository
import tinkoff.model.Tweet
import tinkoff.model.TweetRepository
import tinkoff.service.twitter.TwitterClient
import java.time.LocalDateTime

@Service
class TrackingService(
    private val tweetRepository: TweetRepository,
    private val likesRecordRepository: LikesRecordRepository,
    private val twitterClient: TwitterClient
) {

    suspend fun trackTweetById(id: String): ResponseEntity<String> {
        CoroutineScope(Dispatchers.IO).launch {
            tweetRepository.add(Tweet(id, ))
        }
        CoroutineScope(Dispatchers.Default).launch {
            val likesCount = twitterClient.getLikesCount(id)
            withContext(Dispatchers.IO) {
                likesRecordRepository.addRecord(
                    LikesRecord(id, likesCount, LocalDateTime.now())
                )
            }
        }
        return ResponseEntity.ok("Start tracking tweet with id=$id.")
    }

    suspend fun trackTweetsByAuthor(authorUsername: String, tweetsCount: Int): ResponseEntity<String> {
        TODO()
    }

    suspend fun untrackTweet(id: String): ResponseEntity<String> {
        TODO()
    }

    suspend fun getLikesById(id: String): ResponseEntity<Tweet> {
        TODO()
    }

    suspend fun getLikesForAllTrackedTweets(): ResponseEntity<List<Tweet>> {
        TODO()
    }
}