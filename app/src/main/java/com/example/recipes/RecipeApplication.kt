package com.example.recipes

import android.app.Application
import com.example.recipes.koin.viewModelsModule
import com.example.data.di.networkModule
import com.example.data.di.productRepositoryModule
import com.example.data.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RecipeApplication)
            modules(
                productRepositoryModule,
                viewModelsModule,
                useCaseModule,
                networkModule
            )
        }
    }
}