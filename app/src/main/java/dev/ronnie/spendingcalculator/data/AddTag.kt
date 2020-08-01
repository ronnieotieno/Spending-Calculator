package dev.ronnie.spendingcalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class AddTag(
    val tag: String,
    @PrimaryKey(autoGenerate = false)
    val id: String
) {
}