package dev.ronnie.spendingcalculator.viewModels

import androidx.lifecycle.ViewModel
import dev.ronnie.spendingcalculator.data.SmsRepository
import java.text.DecimalFormat
import java.text.NumberFormat

class FragmentPieChartViewModel(private val smsRepository: SmsRepository) : ViewModel() {

    val sms = smsRepository.getMessages()


    fun getFomartedAmount(amount: Double) = formatCurrency(amount)

    private fun formatCurrency(number: Double): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(number)

        return "$formattedNumber.00"
    }
}