package tinkoff.service.twitter

import com.fasterxml.jackson.annotation.JsonProperty

data class TweetInformation(
    val data: Data,
    val includes: Includes
) {
    data class Data(
        @JsonProperty("id")
        val id: String,
        val text: String,
        @JsonProperty("author_id")
        val authorId: String
    )
    data class Includes(
        val users: List<User>
    ) {
        data class User(
            val id: String,
            val name: String,
            val username: String
        )
    }
}