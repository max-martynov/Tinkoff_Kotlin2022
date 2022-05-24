package tinkoff.service

import kotlinx.coroutines.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.chart.ChartBuilder
import tinkoff.model.*
import tinkoff.service.twitter.TwitterClient
import java.time.LocalDateTime

@Service
class TrackingService(
    private val tweetsRepository: TweetsRepository,
    private val likesRecordsRepository: LikesRecordsRepository,
    private val twitterClient: TwitterClient
) {

    private val chartBuilder = ChartBuilder()

    suspend fun trackTweetById(id: String): ResponseEntity<String> {
        val existedTweet = tweetsRepository.getById(id)
        if (existedTweet != null) {
            if (existedTweet.status == TweetStatus.TRACKED)
                throw IllegalArgumentException("Tweet with id=$id is already tracked.")
            else {
                tweetsRepository.updateStatus(id, TweetStatus.TRACKED)
                return ResponseEntity.ok("Start tracking tweet with id=$id.")
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            processNewTweet(id)
        }
        return ResponseEntity.ok("Request to track tweet with id=$id received.")
    }

    private suspend fun processNewTweet(id: String) = coroutineScope {
        launch {
            val tweet = twitterClient.getTweet(id)
            if (tweetsRepository.isFull())
                throw IllegalArgumentException("Tracked tweets limit = 15 exceeded.")
            withContext(Dispatchers.IO) { tweetsRepository.add(tweet) }
        }
        launch {
            val likesCount = twitterClient.getLikesCount(id)
            withContext(Dispatchers.IO) {
                likesRecordsRepository.addRecord(
                    LikesRecord(id, likesCount, LocalDateTime.now())
                )
            }
        }
    }

    suspend fun untrackTweet(id: String): ResponseEntity<String> {
        CoroutineScope(Dispatchers.IO).launch {
            tweetsRepository.updateStatus(id, TweetStatus.UNTRACKED)
        }
        return ResponseEntity.ok("Request to untrack tweet with id=$id received.")
    }

    suspend fun getLikesRecordsList(tweetId: String): ResponseEntity<TweetResponse> = withContext(Dispatchers.IO) {
        val tweet = async { tweetsRepository.getById(tweetId) ?: throw IllegalArgumentException("No tweet with id=$tweetId") }
        val likesRecords = async { likesRecordsRepository.getRecords(tweetId) }
        return@withContext ResponseEntity.ok(TweetResponse(tweet.await(), likesRecords.await()))
    }

    suspend fun getLikesRecordsChart(tweetId: String): ResponseEntity<String> = withContext(Dispatchers.IO) {
        val likesRecords = async { likesRecordsRepository.getRecords(tweetId) }
        return@withContext ResponseEntity.ok(chartBuilder.build(tweetId, likesRecords.await()))
    }

}
