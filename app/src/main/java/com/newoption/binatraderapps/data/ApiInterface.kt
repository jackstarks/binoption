package com.newoption.binatraderapps.data

import com.newoption.binatraderapps.model.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {
    @GET("WJktfdFQ")
    suspend fun getProjectList(
        @Query("af_status") afStatus: String,
        @Query("user_country") userCountry: String
    ): DataModel

    @GET
    suspend fun getProjectListUrl(
        @Url url: String
    ): Response<DataModel>

}