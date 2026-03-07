package com.adityaproj.parseai.di

import com.adityaproj.parseai.Authapis.Authentication
import com.adityaproj.parseai.Authapis.AuthRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  Signupnetworkmodule {

    @Provides
    @Singleton
    fun provideAuthApi(): Authentication {
        return AuthRetrofit.api
    }

}