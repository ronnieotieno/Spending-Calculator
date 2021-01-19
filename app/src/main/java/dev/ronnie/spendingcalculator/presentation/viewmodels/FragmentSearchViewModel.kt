package dev.ronnie.spendingcalculator.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import dev.ronnie.spendingcalculator.domain.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentSearchViewModel @ViewModelInject constructor(private val smsRepository: SmsRepository) :
    ViewModel() {

    private var _messageListLiveData: MutableLiveData<List<Message>> = MutableLiveData()
    val messageListLiveData: LiveData<List<Message>> get() = _messageListLiveData

    fun getTaggedMessages(tagString: String) {

        viewModelScope.launch {
            val messageList = smsRepository.getTaggedMessagesFromId(tagString)

            withContext(Dispatchers.Main) {
                _messageListLiveData.value = messageList
            }
        }

    }
}

