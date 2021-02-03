package com.github.alvarosct02.criptocurrency.di

import android.content.Context
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        AppDatabase.init(appContext)
        return AppDatabase.getInstance()
    }
}
