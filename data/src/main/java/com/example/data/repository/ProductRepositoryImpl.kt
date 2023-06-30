package com.example.data.repository

import com.example.data.api.RecipeApi
import com.example.domain.model.Recipe
import com.example.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productService: RecipeApi,): ProductRepository {
    override suspend fun getProducts(page: Int, pageSize: Int): Result<List<Recipe>> {
        return runCatching {
            productService.getProducts(page, pageSize).results
        }
    }
}