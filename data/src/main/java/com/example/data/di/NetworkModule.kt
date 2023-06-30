package com.example.data.di

import com.example.data.api.RecipeApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single (named("clientProduct")){
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("X-RapidAPI-Key", "7ab183b67emsh3f7f3870d1ae5c6p195056jsn30fae2a34bee")
                   // .header("X-RapidAPI-Host","tasty.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single(named("retrofitProduct"))  {
        Retrofit.Builder()
            .client(get(named("clientProduct")))
            .baseUrl("https://tasty.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named("retrofitProduct")).create(RecipeApi::class.java) }
}

