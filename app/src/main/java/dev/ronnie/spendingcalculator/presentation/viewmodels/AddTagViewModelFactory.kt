package dev.ronnie.spendingcalculator.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ronnie.spendingcalculator.data.repository.SmsRepository

class AddTagViewModelFactory(
    private val repository: SmsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTagViewModel(
            repository
        ) as T
    }
}