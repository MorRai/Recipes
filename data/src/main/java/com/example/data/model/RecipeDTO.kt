package com.example.data.model

data class RecipesDTO(
    val count: Int,
    val results: List<RecipeDTO>
)

data class RecipeDTO(
    val id: Int,
    val name: String,
    val thumbnail_url: String//изображение
)