package ru.rtulab.smartdormitory.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import net.openid.appauth.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.rtulab.smartdormitory.common.ResponseHandler
import ru.rtulab.smartdormitory.common.persistence.AuthStateStorage
import ru.rtulab.smartdormitory.data.BasicAuthInterceptor
import ru.rtulab.smartdormitory.data.remote.api.booking.BookingApi
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectApi
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectTypeApi
import ru.rtulab.smartdormitory.data.remote.api.profile.ProfileApi
import ru.rtulab.smartdormitory.data.remote.api.report.ReportApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideBasicAuthInterceptor(
        authStateStorage: AuthStateStorage,
    ): BasicAuthInterceptor =
        BasicAuthInterceptor(authStateStorage)


    private val defaultJson = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        basicAuthInterceptor: BasicAuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(basicAuthInterceptor)
            .build()


    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory =
        defaultJson.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, converter: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://46.175.149.247:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideResponseHandler() = ResponseHandler()

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create()


    @Singleton
    @Provides
    fun provideBookingApi(retrofit: Retrofit): BookingApi = retrofit.create()


    @Singleton
    @Provides
    fun provideObjectApi(retrofit: Retrofit): ObjectApi = retrofit.create()

    @Singleton
    @Provides
    fun provideObjectTypeApi(retrofit: Retrofit): ObjectTypeApi = retrofit.create()

    @Singleton
    @Provides
    fun provideReportApi(retrofit: Retrofit): ReportApi = retrofit.create()
}