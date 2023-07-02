package com.example.data.mapper

import com.example.data.model.RecipeDTO
import com.example.data.model.RecipeEntity
import com.example.domain.model.Recipe

 fun List<RecipeDTO>.toDomainModels(): List<Recipe> {
    return map { it.toDomainModels() }
}


 fun RecipeDTO.toDomainModels(): Recipe {
    return Recipe(
        id = id,
        name = name,
        image = thumbnail_url
    )
}


 fun Recipe.toDomainModels(): RecipeEntity {
    return RecipeEntity(
        id = id,
        name = name,
        image = image,
    )
}

 fun RecipeEntity.toDomainModels(): Recipe {
    return Recipe(
        id = id,
        name = name,
        image = image,
    )
}