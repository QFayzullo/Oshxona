package uz.fayzullo.oshxona.domain

data class Recipe(
    val id: Int,
    val slug: String,
    val lang: String,
    val title: String,
    val primaryCategory: String,
    val imageUrl: String,
    val videoUrl: String,
    val hasVideo: Boolean
)