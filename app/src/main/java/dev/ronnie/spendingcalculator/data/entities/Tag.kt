package dev.ronnie.spendingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ronnie.spendingcalculator.domain.Message
import java.lang.reflect.Type

@Entity(tableName = "tags")
@TypeConverters(DataConverter::class)
data class Tag(
    val tag: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val message: Message
)

class DataConverter {
    private val gSon = Gson()

    @TypeConverter
    fun from(message: Message?): String? {
        if (message == null) {
            return null
        }
        val type: Type = object : TypeToken<Message?>() {}.type
        return gSon.toJson(message, type)
    }

    @TypeConverter
    fun to(messageString: String?): Message? {
        if (messageString == null) {
            return null
        }

        val type: Type = object : TypeToken<Message?>() {}.type
        return gSon.fromJson(messageString, type)
    }
}