package com.example.bce.di

import com.example.bce.BCEApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideApplication(
        bceApplication : BCEApplication
    ) : BCEApplication {
        return bceApplication
    }
}