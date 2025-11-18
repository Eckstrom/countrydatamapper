package gatech.edu.countrydatamapper.mapping

import gatech.edu.countrydatamapper.persistence.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gatech.edu.countrydatamapper.Constants.JSON_URL
import gatech.edu.countrydatamapper.json.CountryApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClient {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(JSON_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCountryApiService(retrofit: Retrofit): CountryApi =
        retrofit.create(CountryApi::class.java)

    @Provides
    @Singleton
    fun provideCountryRepository(
        api: CountryApi
    ): CountryRepository = CountryRepository(api)
}
