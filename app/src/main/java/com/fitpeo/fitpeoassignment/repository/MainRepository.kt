package com.fitpeo.fitpeoassignment.repository

import com.fitpeo.fitpeoassignment.service.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(val apiService: ApiService){

    suspend fun getApiDetails() = apiService.getDetails()
}