package tinkoff.repository

import org.springframework.stereotype.Repository
import tinkoff.model.Tweet

@Repository
interface TweetsRepository {

    suspend fun save(tweet: Tweet)

    suspend fun get(id: Long): Tweet?

    fun clear()
}