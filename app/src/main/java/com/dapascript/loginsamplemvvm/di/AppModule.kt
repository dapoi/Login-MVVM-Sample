package com.dapascript.loginsamplemvvm.di

import com.dapascript.loginsamplemvvm.data.repository.LoginRepository
import com.dapascript.loginsamplemvvm.data.repository.LoginRepositoryImpl
import com.dapascript.loginsamplemvvm.data.source.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Create API service
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }
}