package com.example.recipes.ui.recipe_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.domain.model.Recipe
import org.koin.androidx.compose.koinViewModel

@Composable
@ExperimentalMaterial3Api
fun ListRecipesScreen(
    navigateToRecipeDetailScreen: (recipeId: Int) -> Unit
) {

    val viewModel = koinViewModel<RecipeListViewModel>()
    val recipeFlowState = viewModel.recipePagingDataFlow.collectAsLazyPagingItems()
    RecipesGrid(recipes = recipeFlowState,navigateToRecipeDetailScreen)

}


@Composable
@ExperimentalMaterial3Api
fun RecipeCard(
    drawable: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(3.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.width(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
@ExperimentalMaterial3Api
fun RecipesGrid(
    recipes: LazyPagingItems<Recipe>,
    navigateToRecipeDetailScreen: (recipeId: Int) -> Unit,
    modifier: Modifier = Modifier,

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.background(Color.LightGray)
    ) {

        items(recipes.itemCount) { index ->
            recipes[index]?.let {
                RecipeCard(it.image, it.name,
                    onClick = {
                        navigateToRecipeDetailScreen(it.id)
                    }, modifier = Modifier.height(270.dp))

            }
        }

        val loadState = recipes.loadState.mediator
        item {
            if (loadState?.refresh == LoadState.Loading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Refresh Loading"
                    )

                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            if (loadState?.append == LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError =
                    (loadState.append is LoadState.Error) || recipes.itemCount > 1
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillMaxSize()
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Rounded.Warning, contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = error.message ?: error.toString(),
                        textAlign = TextAlign.Center,
                    )

                    Button(
                        onClick = {
                            recipes.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        },
                        colors = ButtonDefaults.buttonColors(
                            //background = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                        )
                    )
                }
            }
        }
    }
}