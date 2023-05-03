package com.fitpeo.fitpeoassignment.service


import com.fitpeo.fitpeoassignment.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    suspend fun getDetails(): Response<List<Album>>
}