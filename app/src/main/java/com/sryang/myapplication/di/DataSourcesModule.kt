package com.sryang.myapplication.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    /*@Binds
    abstract fun provideLocalDataSource(myReviewsLocalDataSourceImpl: MyReviewsLocalDataSourceImpl): MyReviewsLocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(myReviewsRemoteDataSourceImpl: MyReviewsRemoteDataSourceImpl): MyReviewsRemoteDataSource*/
}