package com.example.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Recipe
import com.example.recipes.ui.theme.RecipesTheme
import com.example.domain.model.Response
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RecipesTheme {
                Surface(tonalElevation = 5.dp) {
                    ListRecipesScreen()
                }
            }
        }
    }
}

@Composable
fun ListRecipesScreen() {

    val viewModel = koinViewModel<RecipeListViewModel>()
    val recipeFlowState = viewModel.recipeFlow.collectAsState().value

    when (recipeFlowState) {
        is Response.Loading -> {
            // Отображение состояния загрузки
        }
        is Response.Success -> {
            val recipes = recipeFlowState.data
            RecipesGrid(recipes)
        }
        is Response.Failure -> {
            val errorMessage = recipeFlowState.e
            // Отображение ошибки
        }
    }
}



@Composable
fun RecipeCard(
    drawable: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(modifier = Modifier.width(150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = drawable,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(150.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun RecipesGrid(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.background(Color.LightGray)
    ) {
        items(recipes) { item ->
            RecipeCard(item.image, item.name, modifier = Modifier.height(270.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecipesTheme {
        ListRecipesScreen()
    }
}