package tinkoff.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.Tweet
import tinkoff.service.TweetService

@RestController
class TweetController(private val tweetService: TweetService) {

    @PostMapping("/start-tracking")
    suspend fun startTrackingTweet(@RequestParam id: Long): ResponseEntity<String> {
        return tweetService.startTrackingTweet(id)
    }

    @GetMapping("/get-info/{id}")
    suspend fun getTweetInfo(@PathVariable id: Long): ResponseEntity<Tweet> {
        return tweetService.getTweetInfo(id)
    }

}