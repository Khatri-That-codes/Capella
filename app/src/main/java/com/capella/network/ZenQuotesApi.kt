
package com.capella.network

import com.capella.models.QuoteDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory

interface ZenQuotesApi {
    @GET("api/random")
    suspend fun getRandomQuote(): List<QuoteDTO>

    companion object {
        const val BASE_URL = "https://zenquotes.io/"

        fun create(): ZenQuotesApi {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ZenQuotesApi::class.java)
        }
    }
}
