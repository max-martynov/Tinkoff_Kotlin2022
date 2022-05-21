package tinkoff.model

import java.time.LocalDateTime

data class Tweet(
    val id: String,
    val text: String,
    val authorUsername: String,
    val status: TweetStatus
)

enum class TweetStatus {
    TRACKED, UNTRACKED
}

