package tinkoff.repository.likes.db

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import tinkoff.model.LikesRecord
import tinkoff.model.LikesRecordsRepository

@Primary
@Repository
class DBLikesRecordsRepository : LikesRecordsRepository {
    override fun addRecord(record: LikesRecord): Unit = transaction {
        LikesRecordsTable.insert {
            it[tweetId] = record.tweetId
            it[likesCount] = record.likesCount
            it[relevantAt] = record.relevantAt
        }
    }

    override fun getRecords(tweetId: String): List<LikesRecord> = transaction {
        LikesRecordsTable.select { LikesRecordsTable.tweetId eq tweetId }.map { it.toLikesRecord() }
    }

    private fun ResultRow.toLikesRecord() = LikesRecord(
        this[LikesRecordsTable.tweetId],
        this[LikesRecordsTable.likesCount],
        this[LikesRecordsTable.relevantAt]
    )

    override fun clear(): Unit = transaction {
        LikesRecordsTable.deleteAll()
    }
}