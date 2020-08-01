package dev.ronnie.spendingcalculator.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.regex.Pattern

class SmsDataSource(private val context: Context) {
    private val creditList = arrayListOf<Message>()
    private val debitList = arrayListOf<Message>()
    private var creditAmount = 0.0
    private var debitAmount = 0.0

    fun getSms(): LiveData<SmsData> {
        val messageList = arrayListOf<Message>()

        val cursor =
            context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)

        while (cursor != null && cursor.moveToNext()) {

            val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
            val id = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID))

            messageList.add(
                Message(
                    number,
                    body,
                    Date(smsDate.toLong()),
                    id
                )
            )
        }

        cursor?.close()

        for (message in messageList) {

            checkIfMessageIsTransactional(message)
        }

        val smsLiveData = MutableLiveData<SmsData>()
        smsLiveData.value = SmsData(
            creditList,
            debitList,
            creditAmount,
            debitAmount
        )

        return smsLiveData
    }

    private fun checkIfMessageIsTransactional(message: Message) {
        var amount: String? = ""
        val rgx =
            Pattern.compile("[rR][sS]\\.?\\s[,\\d]+\\.?\\d{0,2}|[iI][nN][rR]\\.?\\s*[,\\d]+\\.?\\d{0,2}| [kK][sS][hH]\\.?\\s*[,\\d]+\\.?\\d{0,2}")

        val matcher = rgx.matcher(message.body)

        if (matcher.find()) {

            try {
                amount = matcher.group(0)?.replace("inr".toRegex(), "")
                amount = amount?.replace("rs".toRegex(), "")
                amount = amount?.replace("inr".toRegex(), "")
                amount = amount?.replace(" ".toRegex(), "")
                amount = amount?.replace(",".toRegex(), "")

            } catch (e: Exception) {

            }

        }
        try {
            amount = amount?.removePrefix("Rs")
            amount = amount?.removePrefix("Ksh")
            amount = amount?.removePrefix("KSh")
            amount = amount?.removePrefix("KSH")
        } catch (e: Exception) {

        }
        if (message.body.contains("Credited") || message.body.contains("credited") || message.body.contains(
                "received"
            ) || message.body.contains(
                "Cr"
            )
        ) {

            try {
                if (!amount.isNullOrEmpty()) {
                    creditAmount += amount.toDouble()
                    creditList.add(message)
                }
            } catch (e: Exception) {

            }
        }
        if (message.body.contains("Debited") || message.body.contains("debited") || message.body.contains(
                "Withdrawn"
            ) || message.body.contains(
                "sent"
            ) || message.body.contains("purchase") || message.body.contains("purchased") || message.body.contains(
                "purchasing"
            )
        ) {

            try {
                if (!amount.isNullOrEmpty()) {
                    debitAmount += amount.toDouble()
                    debitList.add(message)
                }
            } catch (e: Exception) {

            }
        }


    }

    suspend fun searchTags(tagString: String, taggedSmsDao: TaggedSmsDao): List<Message> {
        val messageList = ArrayList<Message>()
        val list = taggedSmsDao.getTags(tagString)

        Log.d("ListHere", list.toString())
        for (i in list.indices) {

            val myMessage: Uri = Uri.parse("content://sms/")
            val cr = context.contentResolver
            val c: Cursor = cr.query(
                myMessage,
                arrayOf("_id", "address", "date", "body", "read"),
                "_id = ${list[i].id}",
                null,
                null
            )!!

            c.moveToFirst()

            val number: String = c.getString(c.getColumnIndexOrThrow("address")).toString()

            val body: String = c.getString(c.getColumnIndexOrThrow("body")).toString()
            val date: String = c.getString(c.getColumnIndexOrThrow("date")).toString()

            messageList.add(
                Message(
                    number,
                    body,
                    Date(date.toLong()),
                    list[i].id
                )
            )


        }
        return messageList
    }

    companion object {

        @Volatile
        private var instance: SmsDataSource? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SmsDataSource(context).also { instance = it }
            }
    }
}