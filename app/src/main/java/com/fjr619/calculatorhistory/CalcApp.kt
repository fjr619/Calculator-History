package com.fjr619.calculatorhistory

import android.app.Application
import com.fjr619.calculatorhistory.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class CalcApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalcApp)
            modules(
                AppModule().module
            )
        }
    }
}