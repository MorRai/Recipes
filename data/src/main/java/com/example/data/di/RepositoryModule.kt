package com.example.data.di

import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.ProductRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val productRepositoryModule = module {
    singleOf(::ProductRepositoryImpl){bind<ProductRepository>() }
}