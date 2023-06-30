package com.example.data.api

import com.example.domain.model.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/list")
    suspend fun getProducts(
        @Query("from") from: Int,
        @Query("size") size: Int
    ): RecipeListResponse
}