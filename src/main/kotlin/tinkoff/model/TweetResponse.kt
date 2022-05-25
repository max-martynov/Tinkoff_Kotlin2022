package tinkoff.model

data class TweetResponse(
    val tweet: Tweet,
    val likesRecords: List<LikesRecord>
)