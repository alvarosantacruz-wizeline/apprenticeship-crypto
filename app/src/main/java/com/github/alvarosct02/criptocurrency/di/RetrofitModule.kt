package com.github.alvarosct02.criptocurrency.di

import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofitClient(): Retrofit {
        return RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
    }
}
