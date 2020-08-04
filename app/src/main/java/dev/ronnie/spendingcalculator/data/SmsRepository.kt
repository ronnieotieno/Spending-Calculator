package dev.ronnie.spendingcalculator.data

import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.entities.AddTag

class SmsRepository(
    private val smsDataSource: SmsDataSource,
    private val taggedSmsDao: TaggedSmsDao
) {

    fun getMessages() = smsDataSource.getSms()
    suspend fun checkIfMessageIsSaved(id: String) = taggedSmsDao.getTagsWithId(id)?.tag
    suspend fun insertTaggedMessageId(tag: AddTag) = taggedSmsDao.insertTag(tag)
    suspend fun getTaggedMessagesFromId(tagString: String) =
        smsDataSource.searchTags(tagString, taggedSmsDao)

    companion object {

        @Volatile
        private var instance: SmsRepository? = null

        fun getInstance(smsDataSource: SmsDataSource, taggedSmsDao: TaggedSmsDao) =
            instance ?: synchronized(this) {
                instance ?: SmsRepository(
                    smsDataSource,
                    taggedSmsDao
                ).also { instance = it }
            }
    }
}