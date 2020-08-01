package dev.ronnie.spendingcalculator.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Parcelize
data class SmsData(
    val creditSmsList: List<Message>,
    val DebitSmsList: List<Message>,
    val TotalCreditedAmount: Double,
    val totalDebitedAmount: Double
) : Flow<SmsData>, Parcelable {
    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<SmsData>) {
        TODO("Not yet implemented")
    }
}