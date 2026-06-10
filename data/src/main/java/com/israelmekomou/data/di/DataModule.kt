package com.israelmekomou.data.di

import android.content.Context
import androidx.room.Room
import com.israelmekomou.data.local.dao.MovieDao
import com.israelmekomou.data.local.database.CineScopeDatabase
import com.israelmekomou.data.remote.api.TmdbApi
import com.israelmekomou.data.repository.MovieRepositoryImpl
import com.israelmekomou.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi =
        retrofit.create(TmdbApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CineScopeDatabase =
        Room.databaseBuilder(context, CineScopeDatabase::class.java, "cinescope.db")
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(database: CineScopeDatabase): MovieDao = database.movieDao()

    @Provides
    @Singleton
    fun provideMovieRepository(
        tmdbApi: TmdbApi,
        movieDao: MovieDao
    ): MovieRepository = MovieRepositoryImpl(tmdbApi, movieDao)
}