package dev.ronnie.spendingcalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ronnie.spendingcalculator.data.entities.Tag

@Dao
interface TaggedSmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Query("SELECT * FROM tags WHERE tag  LIKE '%' || :tag || '%'")
    suspend fun getTags(tag: String): List<Tag>

    @Query("SELECT * FROM tags WHERE id = :id")

    suspend fun getTagsWithId(id: String): Tag?

}