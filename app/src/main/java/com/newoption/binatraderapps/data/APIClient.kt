package com.newoption.binatraderapps.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


object APIClient {
    var gson = GsonBuilder()
        .setLenient()
        .create()
    private const val baseURL: String = "https://benchhman.xyz/"
    val apiClient: ApiInterface = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)
    val apiClient2: ApiInterface = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiInterface::class.java)
}