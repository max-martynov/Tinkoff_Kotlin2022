package tinkoff.model

import java.time.LocalDateTime

data class LikesRecord(
    val tweetId: String,
    val likesCount: Int,
    val relevantAt: LocalDateTime
)
