package dev.ronnie.spendingcalculator.data.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import dev.ronnie.spendingcalculator.data.testUtils.getValue
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentPieChartViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FragmentPieChartViewModelTest : ViewModel() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var smsRepository: SmsRepository
    private lateinit var smsDataSource: SmsDataSource
    private lateinit var viewModel: FragmentPieChartViewModel
    private lateinit var taggedSmsDao: TaggedSmsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        taggedSmsDao = appDatabase.getTagDao()

        smsDataSource = SmsDataSource(context)
        smsRepository = SmsRepository(smsDataSource, taggedSmsDao)

        viewModel = FragmentPieChartViewModel(smsRepository)

    }

    @After
    fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }

    @Test
    fun getTaggedMessages() = runBlocking {

        ViewMatchers.assertThat(
            getValue(viewModel.sms).creditSmsList[0],
            equalTo(getValue(smsDataSource.getSms()).creditSmsList[0])
        )

    }

    @Test
    fun testNumberFormat() = runBlocking {
        val testFormat = viewModel.getfomartedAmount(20000.00)
        ViewMatchers.assertThat(
            testFormat,
            equalTo("20,000.00")
        )

    }

}