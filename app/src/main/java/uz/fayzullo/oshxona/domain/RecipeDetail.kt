package uz.fayzullo.oshxona.domain

data class RecipeDetail(
    val id: Int,
    val slug: String,
    val lang: String,
    val title: String,
    val primaryCategory: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val steps: List<RecipeStep>,
    val imageUrl: String,
    val videoUrl: String,
    val author: String,
    val publishedDate: String,
    val url: String,
    val hasVideo: Boolean
)