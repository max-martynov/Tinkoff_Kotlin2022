package tinkoff.model


data class Tweet(
    val id: String,
    val text: String,
    val authorUsername: String,
    val status: TweetStatus
)

enum class TweetStatus {
    TRACKED, UNTRACKED
}

