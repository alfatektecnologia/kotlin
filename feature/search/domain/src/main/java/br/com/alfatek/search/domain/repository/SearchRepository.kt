package br.com.alfatek.search.domain.repository

import br.com.alfatek.search.domain.model.Recipe
import br.com.alfatek.search.domain.model.RecipeDetails

interface SearchRepository {
    suspend fun getRecipes(s:String): Result<List<Recipe>>
    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>
}