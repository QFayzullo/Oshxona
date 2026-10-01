package uz.fayzullo.oshxona.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.fayzullo.oshxona.data.remote.model.CategoryDto
import uz.fayzullo.oshxona.data.remote.model.PaginatedRecipesDto
import uz.fayzullo.oshxona.data.remote.model.RecipeFullDto

interface OshxonaApiService {

    @GET("api/v1/recipes/")
    suspend fun getRecipes(
        @Query("lang") lang: String = "uz",
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20
    ): PaginatedRecipesDto

    @GET("api/v1/recipes/random")
    suspend fun getRandomRecipe(
        @Query("lang") lang: String = "uz",
        @Query("category") category: String? = null
    ): RecipeFullDto

    @GET("api/v1/recipes/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") id: Int
    ): RecipeFullDto

    @GET("api/v1/categories/")
    suspend fun getCategories(): List<CategoryDto>

    @GET("api/v1/categories/{key}/recipes")
    suspend fun getCategoryRecipes(
        @Path("key") key: String,
        @Query("lang") lang: String = "uz",
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20
    ): PaginatedRecipesDto

    @GET("api/v1/search/")
    suspend fun searchRecipes(
        @Query("q") query: String,
        @Query("lang") lang: String = "uz",
        @Query("limit") limit: Int = 40
    ): PaginatedRecipesDto

    @GET("api/v1/search/ingredients")
    suspend fun searchByIngredients(
        @Query("q") ingredients: String,
        @Query("lang") lang: String = "uz",
        @Query("limit") limit: Int = 40
    ): PaginatedRecipesDto
}