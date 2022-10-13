package com.example.notesapp.di

import com.example.notesapp.api.AuthInterceptor
import com.example.notesapp.api.NoteAPI
import com.example.notesapp.api.UserRequestApi
import com.example.notesapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun  provideRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    }

    @Singleton
    @Provides
    fun provideOkhttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providerUserApi(retrofitBuilder: Retrofit.Builder):UserRequestApi {
        return retrofitBuilder.build().create(UserRequestApi::class.java)
    }


    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NoteAPI {
        return retrofitBuilder.client(okHttpClient).build().create(NoteAPI::class.java)
    }
}