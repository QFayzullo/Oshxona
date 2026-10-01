package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeFullDto(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("lang") val lang: String,
    @SerializedName("title") val title: String,
    @SerializedName("primary_category") val primaryCategory: String,
    @SerializedName("description") val description: String,
    @SerializedName("ingredients") val ingredients: List<IngredientDto>,
    @SerializedName("steps") val steps: List<RecipeStepDto>,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("video_url") val videoUrl: String,
    @SerializedName("author") val author: String?,
    @SerializedName("published_date") val publishedDate: String?,
    @SerializedName("url") val url: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("has_video") val hasVideo: Boolean
)