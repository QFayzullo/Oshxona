package uz.fayzullo.oshxona.data.repository

import uz.fayzullo.oshxona.data.remote.OshxonaApiService
import uz.fayzullo.oshxona.data.remote.model.IngredientDto
import uz.fayzullo.oshxona.data.remote.model.RecipeFullDto
import uz.fayzullo.oshxona.data.remote.model.RecipeShortDto
import uz.fayzullo.oshxona.data.remote.model.RecipeStepDto
import uz.fayzullo.oshxona.domain.Category
import uz.fayzullo.oshxona.domain.Ingredient
import uz.fayzullo.oshxona.domain.Recipe
import uz.fayzullo.oshxona.domain.RecipeDetail
import uz.fayzullo.oshxona.domain.RecipeStep
import uz.fayzullo.oshxona.domain.repository.OshxonaRepository
import javax.inject.Inject

class OshxonaRepositoryImpl @Inject constructor(
    private val apiService: OshxonaApiService
) : OshxonaRepository {

    override suspend fun getRecipes(page: Int): Result<List<Recipe>> = runCatching {
        apiService.getRecipes(page = page).items.map { it.toDomain() }
    }

    override suspend fun getRandomRecipe(category: String?): Result<RecipeDetail> = runCatching {
        apiService.getRandomRecipe(category = category).toDomain()
    }

    override suspend fun getRecipeById(id: Int): Result<RecipeDetail> = runCatching {
        apiService.getRecipeById(id).toDomain()
    }

    override suspend fun getCategories(): Result<List<Category>> = runCatching {
        apiService.getCategories().map { dto ->
            Category(
                key = dto.key,
                nameUz = dto.nameUz,
                nameRu = dto.nameRu,
                icon = dto.icon,
                count = dto.count
            )
        }
    }

    override suspend fun getCategoryRecipes(categoryKey: String, page: Int): Result<List<Recipe>> = runCatching {
        apiService.getCategoryRecipes(key = categoryKey, page = page).items.map { it.toDomain() }
    }

    override suspend fun searchRecipes(query: String): Result<List<Recipe>> = runCatching {
        apiService.searchRecipes(query = query).items.map { it.toDomain() }
    }

    override suspend fun searchByIngredients(ingredients: String): Result<List<Recipe>> = runCatching {
        apiService.searchByIngredients(ingredients = ingredients).items.map { it.toDomain() }
    }

    private fun RecipeShortDto.toDomain() = Recipe(
        id = id,
        slug = slug ?: "",
        lang = lang ?: "",
        title = title,
        primaryCategory = primaryCategory ?: "",
        imageUrl = imageUrl ?: "",
        videoUrl = videoUrl ?: "",
        hasVideo = hasVideo ?: false
    )

    private fun IngredientDto.toDomain(): Ingredient {
        val label = if (type == "heading") (text ?: name ?: "") else (name ?: "")
        return Ingredient(
            type = type,
            amount = amount ?: "",
            name = label
        )
    }

    private fun RecipeStepDto.toDomain() = RecipeStep(
        stepNum = stepNum ?: 0,
        stepLabel = stepLabel ?: step ?: "",
        text = text,
        images = images ?: emptyList()
    )

    private fun RecipeFullDto.toDomain() = RecipeDetail(
        id = id,
        slug = slug,
        lang = lang,
        title = title,
        primaryCategory = primaryCategory,
        description = description,
        ingredients = ingredients.map { it.toDomain() },
        steps = steps.map { it.toDomain() },
        imageUrl = imageUrl,
        videoUrl = videoUrl,
        author = author ?: "",
        publishedDate = publishedDate ?: "",
        url = url,
        hasVideo = hasVideo
    )
}