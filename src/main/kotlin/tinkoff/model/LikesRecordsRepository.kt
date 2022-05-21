package tinkoff.model

interface LikesRecordsRepository {
    fun addRecord(record: LikesRecord)
    fun getRecords(tweetId: String): List<LikesRecord>
}