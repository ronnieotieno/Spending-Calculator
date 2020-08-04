package dev.ronnie.spendingcalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ronnie.spendingcalculator.data.entities.AddTag

@Dao
interface TaggedSmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(addTag: AddTag): Long

    @Query("SELECT * FROM tags WHERE tag  LIKE '%' || :tag || '%'")
    suspend fun getTags(tag: String): List<AddTag>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagsWithId(id: String): AddTag?
}