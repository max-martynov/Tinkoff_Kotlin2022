package tinkoff.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.cglib.proxy.Dispatcher
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.model.Tweet
import tinkoff.repository.TweetsRepository

@Service
class TweetService(private val tweetsRepository: TweetsRepository) {

    fun startTrackingTweet(id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Successfully received tweet id.")
    }

    fun getTweetInfo(id: Long): ResponseEntity<Tweet> {
        return ResponseEntity.ok(Tweet(id, "gg wp", 228))
    }

}