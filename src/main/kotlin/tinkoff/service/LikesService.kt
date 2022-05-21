package tinkoff.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordRepository
import tinkoff.model.TweetRepository
import tinkoff.model.TweetStatus
import tinkoff.service.twitter.TwitterClient
import java.time.LocalDateTime

@Component
class LikesService(
    private val tweetRepository: TweetRepository,
    private val likesRecordRepository: LikesRecordRepository,
    private val twitterClient: TwitterClient
) {

    @OptIn(ObsoleteCoroutinesApi::class)
    private val context = newFixedThreadPoolContext(15, "for likes update")

    @Scheduled(cron = "0 * * * * *")
    fun updateLikesRecords() = CoroutineScope(context).launch {
        tweetRepository.getAll().filter { it.status == TweetStatus.TRACKED }.forEach { tweet ->
            launch {
                println(tweet.id)
                val likesCount = twitterClient.getLikesCount(tweet.id)
                likesRecordRepository.addRecord(
                    LikesRecord(
                        tweet.id,
                        likesCount,
                        LocalDateTime.now()
                    )
                )
            }
        }
    }

}