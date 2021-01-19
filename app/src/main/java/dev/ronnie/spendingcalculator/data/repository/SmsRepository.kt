package dev.ronnie.spendingcalculator.data.repository

import androidx.lifecycle.MutableLiveData
import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.entities.Tag
import dev.ronnie.spendingcalculator.domain.SmsData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsRepository @Inject constructor(
    private val smsDataSource: SmsDataSource,
    private val taggedSmsDao: TaggedSmsDao
) {
    init {
        smsDataSource.getSms()
    }

    fun getSmsLiveData(): MutableLiveData<SmsData> {
        smsDataSource.getSms()
        return smsDataSource.smsLiveData
    }
    suspend fun checkIfMessageIsSaved(id: String) = taggedSmsDao.getTagsWithId(id)?.tag
    suspend fun insertTaggedMessageId(tag: Tag) = taggedSmsDao.insertTag(tag)
    suspend fun getTaggedMessagesFromId(tagString: String) =
        smsDataSource.searchTags(tagString, taggedSmsDao)


}