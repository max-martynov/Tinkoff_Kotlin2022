package tinkoff.service

import kotlinx.coroutines.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository

@Service
class TweetService(
    private val tweetsRepository: TweetsRepository,
    private val twitterClient: TwitterClient
) {

    suspend fun startTrackingTweet(id: Long): ResponseEntity<String> {
        CoroutineScope(Dispatchers.Default).launch {
            val tweet = collectTweetInfo(id)
            withContext(Dispatchers.IO) {
                tweetsRepository.save(tweet)
            }
        }
        return ResponseEntity.ok("Tweet id=$id successfully received!")
    }

    private suspend fun collectTweetInfo(id: Long): Tweet =
        coroutineScope {
            val tweetText: Deferred<String> = async {
                twitterClient.getTweetText(id)
                    ?: throw IllegalArgumentException("No text found for tweet with id=$id")
            }
            val tweetLikesCount: Deferred<Int> = async {
                twitterClient.getLikesCount(id)
                    ?: throw IllegalArgumentException("No likes count found for tweet with id=$id")
            }
            return@coroutineScope Tweet(id, tweetText.await(), tweetLikesCount.await())
        }


    suspend fun getTweetInfo(id: Long): ResponseEntity<Tweet> = withContext(Dispatchers.IO) {
        val tweet = tweetsRepository.get(id) ?: throw IllegalArgumentException("No tweet with id=$id found")
        return@withContext ResponseEntity.ok(tweet)
    }
}