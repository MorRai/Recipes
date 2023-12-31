package com.example.data.api

import com.example.data.model.RecipeDTO
import com.example.data.model.RecipesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/list")
    suspend fun getProducts(
        @Query("from") from: Int,
        @Query("size") size: Int
    ): RecipesDTO

    @GET("recipes/get-more-info")
    suspend fun getRecipe(
        @Query("id") id: Int,
    ): RecipeDTO
}