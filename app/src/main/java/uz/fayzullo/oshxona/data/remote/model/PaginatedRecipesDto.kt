package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName

data class PaginatedRecipesDto(
    @SerializedName("items") val items: List<RecipeShortDto>
)