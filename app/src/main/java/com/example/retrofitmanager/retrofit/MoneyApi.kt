package com.example.retrofitmanager.retrofit

import com.example.retrofitmanager.retrofit.apiData.Money
import retrofit2.http.GET

interface MoneyApi {
    @GET("daily_json.js")
    suspend fun getMonyById(): Money
}