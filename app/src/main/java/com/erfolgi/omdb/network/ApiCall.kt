package com.erfolgi.omdb.network


import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.model.SearchResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiCall {

    //region movie
    @GET("/")
        fun getSearch(
            @Query("apikey") apikey: String?=ApiClient.apiKey,
            @Query("type") type: String?="movie",
            @Query("s") s: String?=null,
            @Query("page") page: Int?=1
        ): Call<SearchResponse>
    @GET("/")
    fun getDetail(
        @Query("apikey") apikey: String?=ApiClient.apiKey,
        @Query("i") id: String?=null
    ): Call<DetailResponse>

    //endregion movie


}