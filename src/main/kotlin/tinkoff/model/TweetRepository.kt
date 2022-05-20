package tinkoff.model

interface TweetRepository {
    fun add(tweet: Tweet)
    fun getById(id: String): Tweet?
    fun getAll(): List<Tweet>
    fun updateStatus(id: String, newStatus: TweetStatus)
    fun updateLikesTrack(id: String, newLikesTrack: LikesTrack)
}