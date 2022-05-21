package tinkoff.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordsRepository
import tinkoff.model.TweetsRepository
import tinkoff.model.TweetStatus
import tinkoff.service.twitter.TwitterClient
import java.time.LocalDateTime

@Component
class LikesService(
    private val tweetsRepository: TweetsRepository,
    private val likesRecordsRepository: LikesRecordsRepository,
    private val twitterClient: TwitterClient
) {

    @OptIn(ObsoleteCoroutinesApi::class)
    private val context = newFixedThreadPoolContext(15, "for likes update")

    @Scheduled(cron = "0 * * * * *")
    fun updateLikesRecords() = CoroutineScope(context).launch {
        tweetsRepository.getAll().filter { it.status == TweetStatus.TRACKED }.forEach { tweet ->
            launch {
                println(tweet.id)
                val likesCount = twitterClient.getLikesCount(tweet.id)
                likesRecordsRepository.addRecord(
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