package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeStepDto(
    @SerializedName("step_num") val stepNum: Int?,
    @SerializedName("step_label") val stepLabel: String?,
    @SerializedName("step") val step: String?,
    @SerializedName("text") val text: String,
    @SerializedName("images") val images: List<String>?
)