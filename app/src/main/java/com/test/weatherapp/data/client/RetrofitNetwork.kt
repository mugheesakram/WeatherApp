package com.test.weatherapp.data.client

import android.content.Context
import com.google.gson.GsonBuilder
import com.test.weatherapp.BuildConfig
import com.test.weatherapp.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val timeoutConnect = 30   //In seconds
private const val timeoutRead = 30   //In seconds

@Singleton
class RetrofitNetwork @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit
    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val url: HttpUrl = original.url.newBuilder()
            .addQueryParameter("appId", "78802aec51fe12a15015279242376366")
            .build()
        val builder = original.newBuilder()
        val request = builder.method(original.method, original.body).url(url).build()
        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }


    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addNetworkInterceptor(logger)
        val connectivityInterceptor: (chain: Interceptor.Chain) -> Response = { chain ->
            if (!context.isNetworkAvailable()) {
                throw IOException()
            }
            chain.proceed(chain.request())
        }
        okHttpBuilder.addNetworkInterceptor(connectivityInterceptor)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}