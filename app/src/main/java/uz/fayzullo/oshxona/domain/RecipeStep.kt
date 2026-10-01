package uz.fayzullo.oshxona.domain

data class RecipeStep(
    val stepNum: Int,
    val stepLabel: String,
    val text: String,
    val images: List<String>
)