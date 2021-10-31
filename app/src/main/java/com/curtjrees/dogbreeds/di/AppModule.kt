package com.curtjrees.dogbreeds.di

import com.curtjrees.dogbreeds.Navigator
import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.api.ApiDogBreedMapper
import com.curtjrees.dogbreeds.data.api.DogBreedsRepository
import com.curtjrees.dogbreeds.data.api.DogBreedsService
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.mappers.DogBreedItemMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    fun provideDogBreedsService(client: OkHttpClient, moshi: Moshi): DogBreedsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(DogBreedsService::class.java)
    }

    @Singleton
    @Provides
    fun provideDogBreedsDataSource(service: DogBreedsService, dispatchers: CoroutineDispatchers): DogBreedsDataSource = DogBreedsRepository(
        service = service,
        dispatchers = dispatchers,
        mapper = ApiDogBreedMapper
    )

    @Singleton
    @Provides
    fun provideNavigator(coroutineDispatchers: CoroutineDispatchers): Navigator = Navigator(coroutineDispatchers)

    @Singleton
    @Provides
    fun provideDogBreedItemMapper(): DogBreedItemMapper = DogBreedItemMapper


}