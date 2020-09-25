package dev.ronnie.spendingcalculator.di

import android.content.Context
import android.provider.Telephony
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.datasource.SmsDataSource
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import dev.ronnie.spendingcalculator.data.repository.SmsRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideTaggedSmsDao(@ApplicationContext appContext: Context): TaggedSmsDao {
        return AppDatabase.invoke(appContext.applicationContext).getTagDao()
    }

    @Provides
    @Singleton
    fun provideSmsDataSource(@ApplicationContext appContext: Context): SmsDataSource {
        return SmsDataSource(appContext)
    }

    @Provides
    @Singleton
    fun provideSmsRepository(
        taggedSmsDao: TaggedSmsDao,
        smsDataSource: SmsDataSource
    ): SmsRepository {
        return SmsRepository(smsDataSource, taggedSmsDao)
    }

}