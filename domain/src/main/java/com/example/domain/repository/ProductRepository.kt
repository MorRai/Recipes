package com.example.domain.repository


import com.example.domain.model.Recipe

interface ProductRepository {
    suspend fun getProducts(page: Int, pageSize: Int):Result<List<Recipe>>
}