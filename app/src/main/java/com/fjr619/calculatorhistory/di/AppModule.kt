package com.fjr619.calculatorhistory.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.data")
class DataModule

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.domain")
class DomainModule

@Module
@ComponentScan(value = "com.fjr619.calculatorhistory.presentation")
class PresentationModule

@Module(includes = [DataModule::class, DomainModule::class, PresentationModule::class])
class AppModule