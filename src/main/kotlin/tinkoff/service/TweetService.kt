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
            val tweetText = async { twitterClient.getTweetText(id) }
            val tweetLikesCount = async { twitterClient.getLikesCount(id) }
            val tweet = Tweet(id, tweetText.await(), tweetLikesCount.await())
            withContext(Dispatchers.IO) {
                tweetsRepository.save(tweet)
            }
        }
        return ResponseEntity.ok("Tweet ID successfully received!")
    }

    fun getTweetInfo(id: Long): ResponseEntity<Tweet> {
        return ResponseEntity.ok(tweetsRepository.get(id))
    }

}