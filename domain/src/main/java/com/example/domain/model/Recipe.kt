package com.example.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val thumbnail_url: String//изображение
)

data class RecipeListResponse(
    val count: Int,
    val results: List<Recipe>
)