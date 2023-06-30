package com.example.domain.usecase

import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.repository.ProductRepository


class GetRecipeUseCase(private val recipeRepository: ProductRepository) {
    suspend operator fun invoke() :Response<List<Recipe>> {
            //return  Response.Success(emptyList())
            return recipeRepository.getProducts(1,20)
                .fold(onSuccess = {
                    Response.Success(it)
                }, onFailure = {
                    Response.Failure(Exception(it.message))
                })
    }

}