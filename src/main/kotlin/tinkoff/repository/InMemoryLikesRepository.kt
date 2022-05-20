package tinkoff.repository

import org.springframework.stereotype.Repository
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordRepository

@Repository
class InMemoryLikesRepository : LikesRecordRepository {

    val map = mutableMapOf<String, List<LikesRecord>>()

    override fun addRecord(record: LikesRecord) {
        val records = map.getOrDefault(record.tweetId, listOf())
        map[record.tweetId] = records + record
    }

    override fun getRecords(id: String): List<LikesRecord> {
        return map.getOrDefault(id, listOf())
    }
}