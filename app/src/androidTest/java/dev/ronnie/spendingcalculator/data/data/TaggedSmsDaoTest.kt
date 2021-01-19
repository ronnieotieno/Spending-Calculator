package dev.ronnie.spendingcalculator.data.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.spendingcalculator.data.testUtils.addTag1
import dev.ronnie.spendingcalculator.data.testUtils.addTag2
import dev.ronnie.spendingcalculator.data.testUtils.addTag3
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaggedSmsDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var taggedSmsDao: TaggedSmsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        taggedSmsDao = database.taggedSmsDao

    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetTagsSize() = runBlocking {
        database.taggedSmsDao.insertTag(addTag1)
        database.taggedSmsDao.insertTag(addTag2)

        ViewMatchers.assertThat(taggedSmsDao.getTags("new").size, equalTo(2))
    }

    @Test
    fun testGetTags() = runBlocking {

        database.taggedSmsDao.insertTag(addTag3)
        ViewMatchers.assertThat(taggedSmsDao.getTags("testTag")[0], equalTo(addTag3))

    }

    @Test
    fun testGetTagsWithId() = runBlocking {

        database.taggedSmsDao.insertTag(addTag3)

        ViewMatchers.assertThat(taggedSmsDao.getTagsWithId("testId"), equalTo(addTag3))

    }


}