package dev.ronnie.spendingcalculator.data.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import dev.ronnie.spendingcalculator.data.entities.AddTag
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import dev.ronnie.spendingcalculator.presentation.viewmodels.AddTagViewModel
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentListViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FragmentListViewModelTest() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var smsRepository: SmsRepository
    private lateinit var smsDataSource: SmsDataSource
    private lateinit var viewModel: FragmentListViewModel
    private lateinit var taggedSmsDao: TaggedSmsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        taggedSmsDao = appDatabase.getTagDao()

        smsDataSource = SmsDataSource.getInstance(context)
        smsRepository = SmsRepository.getInstance(smsDataSource, taggedSmsDao)

        viewModel = FragmentListViewModel(smsRepository)

    }

    @After
    fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }

    @Test
    fun checkIfMessagedHasTag() = runBlocking {

        val addTag = AddTag("New Test", "newIdAgain")

        taggedSmsDao.insertTag(addTag)

        assertThat(taggedSmsDao.getTagsWithId("newIdAgain")?.tag, equalTo(addTag.tag))
    }


    @Test
    fun checkIfMessagedHasTagWithNullValue() = runBlocking {

        assertThat(viewModel.checkIfMessagedHasTag("hh"), equalTo(null))
    }


}