package com.example.davaleba_15.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitObject {
    private const val BASE_URL = "https://reqres.in/"
    private val retrofitbuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create()
        ).build()

    fun getRetrofitState() = retrofitbuilder.create(LogRegClient::class.java)

    interface LogRegClient {
        @POST("login")
        suspend fun userFLogin(@Body user: UserFPost): Response<TokenFResponse>

        @POST("register")
        suspend fun userFRegister(@Body user: UserFPost): Response<TokenFResponse>
    }
}