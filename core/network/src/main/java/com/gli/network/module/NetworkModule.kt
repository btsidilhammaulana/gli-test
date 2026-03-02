package com.gli.network.module

import android.content.Context
import com.gli.model.anotation.Token
import com.gli.network.NetworkClient
import com.gli.network.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

  @Provides
  fun provideEndpointServices(
    @ApplicationContext context: Context,
    @Token token: String,
  ): ApiServices {
    return NetworkClient(context, token).create(ApiServices::class.java)
  }
}