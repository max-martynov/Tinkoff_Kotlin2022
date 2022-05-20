package tinkoff.model

interface LikesRecordRepository {
    fun addRecord(record: LikesRecord)
    fun getRecords(id: String): List<LikesRecord>
}