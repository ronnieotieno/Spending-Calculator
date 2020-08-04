package dev.ronnie.spendingcalculator.domain

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
) :  Parcelable