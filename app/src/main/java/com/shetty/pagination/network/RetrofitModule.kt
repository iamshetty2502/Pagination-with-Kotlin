package com.shetty.pagination.network

import com.shetty.pagination.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.AUTH_TOKEN}"
            )
            .build()
        chain.proceed(newRequest)
    }.build()

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().client(client).baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getNetworkAPI(retrofit: Retrofit): INetwork {
        return retrofit.create(INetwork::class.java)
    }
}