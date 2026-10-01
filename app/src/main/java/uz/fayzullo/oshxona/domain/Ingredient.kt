package uz.fayzullo.oshxona.domain

data class Ingredient(
    val type: String,
    val amount: String,
    val name: String
) {
    val isHeading: Boolean get() = type == "heading"
}