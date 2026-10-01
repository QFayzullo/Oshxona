package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName

data class IngredientDto(
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("text") val text: String?
)