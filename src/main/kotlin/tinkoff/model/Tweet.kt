package tinkoff.model

import java.time.LocalDateTime

data class Tweet(
    val id: String,
    val text: String,
    val authorId: String,
    val authorUsername: String,
    val status: TweetStatus,
//    val likesTrack: LikesTrack
)

enum class TweetStatus {
    TRACKED, UNTRACKED
}

data class LikesTrack(
    val likesCount: Int,
    val startTracking: LocalDateTime,
    val lastUpdate: LocalDateTime
)
