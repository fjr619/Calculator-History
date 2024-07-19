package com.fjr619.calculatorhistory.di

import android.content.Context
import com.fjr619.calculatorhistory.data.local.room.HistoryDB
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.data")
class DataModule {

    @Single
    fun getHistoryDB(context: Context) = HistoryDB.getInstance(context)

    @Single
    fun provideHistoryDao(database: HistoryDB) = database.historyDao()
}

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.domain")
class DomainModule

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.ui")
class PresentationModule

@Module(includes = [DataModule::class, DomainModule::class, PresentationModule::class])
class AppModule