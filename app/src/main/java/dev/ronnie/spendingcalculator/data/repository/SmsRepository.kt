package dev.ronnie.spendingcalculator.data.repository

import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.entities.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsRepository @Inject constructor(
    private val smsDataSource: SmsDataSource,
    private val taggedSmsDao: TaggedSmsDao
) {

    fun getMessages() = smsDataSource.getSms()
    suspend fun checkIfMessageIsSaved(id: String) = taggedSmsDao.getTagsWithId(id)?.tag
    suspend fun insertTaggedMessageId(tag: Tag) = taggedSmsDao.insertTag(tag)
    suspend fun getTaggedMessagesFromId(tagString: String) =
        smsDataSource.searchTags(tagString, taggedSmsDao)


}