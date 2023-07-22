package com.example.recipes.navigation

sealed class Screen(val route: String) {
    object ListRecipesScreen: Screen("Recipes")
    object RecipeDetailScreen: Screen("Recipe")
}
