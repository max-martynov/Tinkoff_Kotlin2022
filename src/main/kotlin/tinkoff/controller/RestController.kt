package tinkoff.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.Tweet
import tinkoff.model.TweetResponse
import tinkoff.service.TrackingService

@RestController
class RestController(
    val service: TrackingService
) {

    @PostMapping("/track/tweet/id/{id}")
    suspend fun trackTweetById(@PathVariable id: String): ResponseEntity<String> {
        return service.trackTweetById(id)
    }

    @PostMapping("/track/tweet/author")
    suspend fun trackTweetsByAuthor(
        @RequestParam("username") authorUsername: String,
        @RequestParam("tweets-count") tweetsCount: Int
    ): ResponseEntity<String> {
        return service.trackTweetsByAuthor(authorUsername, tweetsCount)
    }

    @PostMapping("/untrack-tweet/id/{id}")
    suspend fun untrackTweet(@PathVariable id: String): ResponseEntity<String> {
        return service.untrackTweet(id)
    }

    @GetMapping("/tweet/id/{id}")
    suspend fun getLikesById(@PathVariable id: String): ResponseEntity<TweetResponse> {
        return service.getTweetResponse(id)
    }

    @GetMapping("/tweet/all-tracked")
    suspend fun getLikesForAllTrackedTweets(): ResponseEntity<List<Tweet>> {
        return service.getLikesForAllTrackedTweets()
    }
}