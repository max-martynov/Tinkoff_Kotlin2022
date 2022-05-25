package tinkoff.service.twitter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class LikingUsers(
    val data: List<User>?,
    val meta: Meta
) {
    data class User(
        val id: String,
        val name: String,
        val username: String
    )

    data class Meta(
        @JsonProperty("result_count")
        val resultCount: Int,
        @JsonProperty("next_token")
        val nextToken: String?,
    )
}
