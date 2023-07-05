package com.example.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.Recipe
import com.example.domain.usecase.GetRecipeUseCase
import kotlinx.coroutines.flow.Flow

class RecipeListViewModel(getRecipeUseCase: GetRecipeUseCase) : ViewModel(){
    val recipePagingDataFlow: Flow<PagingData<Recipe>> = getRecipeUseCase.invoke()
        .cachedIn(viewModelScope)
}