package uz.fayzullo.oshxona.domain.repository

import uz.fayzullo.oshxona.domain.Category
import uz.fayzullo.oshxona.domain.Recipe
import uz.fayzullo.oshxona.domain.RecipeDetail

interface OshxonaRepository {
    suspend fun getRecipes(page: Int = 0): Result<List<Recipe>>
    suspend fun getRandomRecipe(category: String? = null): Result<RecipeDetail>
    suspend fun getRecipeById(id: Int): Result<RecipeDetail>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getCategoryRecipes(categoryKey: String, page: Int = 0): Result<List<Recipe>>
    suspend fun searchRecipes(query: String): Result<List<Recipe>>
    suspend fun searchByIngredients(ingredients: String): Result<List<Recipe>>
}