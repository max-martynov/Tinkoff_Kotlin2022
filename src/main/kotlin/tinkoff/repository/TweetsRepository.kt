package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Tweet

@Service
interface TweetsRepository {

    suspend fun save(tweet: Tweet)

    suspend fun get(id: Long): Tweet?

    suspend fun clear()
}