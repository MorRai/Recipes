package com.example.recipes.ui.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.Recipe
import com.example.domain.usecase.GetRecipesUseCase
import kotlinx.coroutines.flow.Flow

class RecipeListViewModel(getRecipesUseCase: GetRecipesUseCase) : ViewModel(){
    val recipePagingDataFlow: Flow<PagingData<Recipe>> = getRecipesUseCase.invoke()
        .cachedIn(viewModelScope)
}