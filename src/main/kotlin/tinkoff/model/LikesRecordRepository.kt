package tinkoff.model

interface LikesRecordRepository {
    fun addRecord(record: LikesRecord)
    fun getRecords(tweetId: String): List<LikesRecord>
}