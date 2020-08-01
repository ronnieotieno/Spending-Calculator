package dev.ronnie.spendingcalculator.viewModels

import androidx.lifecycle.ViewModel
import dev.ronnie.spendingcalculator.data.SmsRepository
import kotlinx.coroutines.runBlocking


class FragmentListViewModel(private val smsRepository: SmsRepository) : ViewModel() {


    fun checkIfMessagedHasTag(id: String): String? = runBlocking {
        smsRepository.checkIfMessageIsSaved(id)
    }

}