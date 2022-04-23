package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Tweet

@Service
interface TweetsRepository {

    fun save(tweet: Tweet)

    fun get(id: Long): Tweet?
}