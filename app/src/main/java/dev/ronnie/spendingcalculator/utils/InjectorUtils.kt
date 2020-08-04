package dev.ronnie.spendingcalculator.utils

import android.content.Context
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.presentation.viewmodels.AddTagViewModelFactory
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentListViewModelFactory
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentPieChartViewModelFactory
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentSearchViewModelFactory

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