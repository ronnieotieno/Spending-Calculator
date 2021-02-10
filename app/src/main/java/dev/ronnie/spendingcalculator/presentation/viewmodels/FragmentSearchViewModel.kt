package dev.ronnie.spendingcalculator.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.spendingcalculator.data.entities.Tag
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentSearchViewModel @ViewModelInject constructor(private val smsRepository: SmsRepository) :
    ViewModel() {

    private var _tagListLiveData: MutableLiveData<List<Tag>> = MutableLiveData()
    val tagListLiveData: LiveData<List<Tag>> get() = _tagListLiveData

    fun getTaggedMessages(tagString: String) {

        viewModelScope.launch {
            val messageList = smsRepository.getTaggedMessages(tagString)

            withContext(Dispatchers.Main) {
                _tagListLiveData.value = messageList
            }
        }

    }
}

