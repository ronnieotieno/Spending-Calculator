package dev.ronnie.spendingcalculator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.ronnie.spendingcalculator.data.dao.TaggedSmsDao
import dev.ronnie.spendingcalculator.data.db.AppDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTaggedSmsDao(@ApplicationContext appContext: Context): TaggedSmsDao {
        return AppDatabase.invoke(appContext.applicationContext).taggedSmsDao
    }
}