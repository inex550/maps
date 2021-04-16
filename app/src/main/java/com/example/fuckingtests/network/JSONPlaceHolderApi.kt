package com.example.fuckingtests.network

import com.example.fuckingtests.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object JSONPlaceHolderApi {
    val retrofitService: JSONPlaceHolderService by lazy {
        retrofit.create(JSONPlaceHolderService::class.java)
    }
}


interface JSONPlaceHolderService {

    @GET("users")
    suspend fun loadUsers(): List<User>
}