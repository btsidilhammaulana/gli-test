package com.gli.model.module

import com.gli.model.anotation.Token
import com.gli.test.model.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ModelModule {

  @Provides
  @Token
  fun provideToken(): String {
    return BuildConfig.API_READ_ACCESS_TOKEN
  }
}