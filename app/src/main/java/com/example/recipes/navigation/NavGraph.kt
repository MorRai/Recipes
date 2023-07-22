package com.example.recipes.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipes.RecipeDetailScreen
import  com.example.recipes.navigation.Screen.*
import com.example.recipes.ui.recipe_list.ListRecipesScreen


const val RECIPE_ID = "recipeId"


@ExperimentalMaterial3Api
@Composable
fun NavGraph (
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ListRecipesScreen.route
    ) {
        composable(
            route = ListRecipesScreen.route
        ) {
            ListRecipesScreen(
                navigateToRecipeDetailScreen = { recipeId ->
                    navController.navigate(
                        route = "${RecipeDetailScreen.route}/${recipeId}"
                    )
                }
            )
        }
        composable(
            route = "${RecipeDetailScreen.route}/{$RECIPE_ID}",
            arguments = listOf(
                navArgument(RECIPE_ID) {
                    type = IntType
                }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(RECIPE_ID) ?: 0
            RecipeDetailScreen(
                recipeId = recipeId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}