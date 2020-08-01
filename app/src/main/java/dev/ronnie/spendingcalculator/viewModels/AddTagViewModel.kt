package dev.ronnie.spendingcalculator.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.spendingcalculator.data.AddTag
import dev.ronnie.spendingcalculator.utils.Event
import dev.ronnie.spendingcalculator.data.SmsRepository
import kotlinx.coroutines.launch


class AddTagViewModel(private val smsRepository: SmsRepository) : ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage
    var tag: String? = null
    lateinit var id: String
    lateinit var context: Context

    fun saveTags() {

        if (tag?.trim()?.isEmpty()!!) return

        viewModelScope.launch {
            val newRowId = smsRepository.insertTaggedMessageId(
                AddTag(
                    tag!!,
                    id
                )
            )
            if (newRowId > -1) {
                statusMessage.value =
                    Event("Tag Added Successfully")
            } else {
                statusMessage.value =
                    Event("Error Occurred")
            }
        }

    }


}