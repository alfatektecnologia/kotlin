package br.com.alfatek.search.data.repository

import br.com.alfatek.search.domain.model.Recipe
import br.com.alfatek.search.domain.model.RecipeDetails
import br.com.alfatek.search.domain.repository.SearchRepository
import br.com.alfatek.search.data.mappers.toDomain
import br.com.alfatek.search.data.remote.SearchApiService

class SearchRepoImpl(private val searchApiService: SearchApiService) : SearchRepository {
    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return try {
            val response = searchApiService.getRecipes(s)
            if (response.isSuccessful){
                response.body()?.meals?.let {
                    Result.success((it.toDomain()))
                }?:run { Result.failure(Exception("error getting recipes")) }
            }else {
                Result.failure(Exception("error getting recipes"))
            }
        }catch (e:Exception){
            Result.failure<List<Recipe>>(e)
        }

    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(id)
             if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if(it.isNotEmpty()){
                        Result.success(it.first().toDomain())
                    }else{
                        Result.failure(Exception("error getting recipe detail"))
                    }
                } ?: run{
                    Result.failure(Exception("error getting recipe detail"))
                }
            }else {
                Result.failure(Exception("error getting recipe detail"))
            }
        }catch (e: Exception){
            Result.failure<RecipeDetails>(e)
        }
    }
}