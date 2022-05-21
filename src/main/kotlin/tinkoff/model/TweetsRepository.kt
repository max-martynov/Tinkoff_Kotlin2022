package tinkoff.model

interface TweetsRepository {
    fun add(tweet: Tweet)
    fun getById(id: String): Tweet?
    fun getAll(): Set<Tweet>
    fun updateStatus(id: String, newStatus: TweetStatus)
    fun isFull(): Boolean {
        return getAll().size == 15
    }
}