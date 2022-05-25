package tinkoff.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.TweetResponse
import tinkoff.service.TrackingService


@RestController
class RestController(
    val service: TrackingService
) {

    @PostMapping("/track/tweet/{id}")
    suspend fun trackTweetById(@PathVariable id: String): ResponseEntity<String> {
        return service.trackTweetById(id)
    }

    @PostMapping("/untrack/tweet/{id}")
    suspend fun untrackTweet(@PathVariable id: String): ResponseEntity<String> {
        return service.untrackTweet(id)
    }

    @GetMapping("/likes/list/{id}")
    suspend fun getLikesRecordsList(@PathVariable id: String): ResponseEntity<TweetResponse> {
        return service.getLikesRecordsList(id)
    }

    @GetMapping("/likes/chart/{id}", produces = [MediaType.TEXT_HTML_VALUE])
    suspend fun getLikesRecordsChart(@PathVariable id: String): ResponseEntity<String> {
        return service.getLikesRecordsChart(id)
    }
}