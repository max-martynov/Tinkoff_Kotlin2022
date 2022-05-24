package tinkoff.repository.likes

import org.springframework.stereotype.Repository
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordsRepository

@Repository
class InMemoryLikesRepository : LikesRecordsRepository {

    val map = mutableMapOf<String, List<LikesRecord>>()

    override fun addRecord(record: LikesRecord) {
        val records = map.getOrDefault(record.tweetId, listOf())
        map[record.tweetId] = records + record
    }

    override fun getRecords(tweetId: String): List<LikesRecord> {
        return map.getOrDefault(tweetId, listOf())
    }

    override fun clear() {
        map.clear()
    }
}