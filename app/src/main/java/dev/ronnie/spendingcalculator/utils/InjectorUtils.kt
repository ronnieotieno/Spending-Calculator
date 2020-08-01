package dev.ronnie.spendingcalculator.utils

import android.content.Context
import dev.ronnie.spendingcalculator.data.AppDatabase
import dev.ronnie.spendingcalculator.data.SmsDataSource
import dev.ronnie.spendingcalculator.data.SmsRepository
import dev.ronnie.spendingcalculator.data.TaggedSmsDao
import dev.ronnie.spendingcalculator.viewModels.*

object InjectorUtils {

    private fun getSmsDataSource(context: Context): SmsDataSource {
        return SmsDataSource.getInstance(context.applicationContext)
    }

    private fun getSmsRepository(context: Context): SmsRepository {
        return SmsRepository.getInstance(
            getSmsDataSource(
                context.applicationContext
            ),
            getTaggedSmsDao(
                context
            )
        )
    }

    private fun getTaggedSmsDao(context: Context): TaggedSmsDao {
        return AppDatabase.invoke(context.applicationContext).getTagDao()
    }

    fun provideFragmentPieViewModelFactory(context: Context): FragmentPieChartViewModelFactory {

        return FragmentPieChartViewModelFactory(
            getSmsRepository(
                context
            )
        )

    }

    fun provideFragmentListViewModelFactory(context: Context): FragmentListViewModelFactory {

        return FragmentListViewModelFactory(
            getSmsRepository(
                context
            )
        )

    }

    fun provideAddTagViewModelFactory(context: Context): AddTagViewModelFactory {

        return AddTagViewModelFactory(
            getSmsRepository(
                context
            )
        )

    }

    fun provideSearchViewModelFactory(context: Context): FragmentSearchViewModelFactory {

        return FragmentSearchViewModelFactory(
            getSmsRepository(
                context
            )
        )

    }

}