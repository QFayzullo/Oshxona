package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("key") val key: String,
    @SerializedName("name_uz") val nameUz: String,
    @SerializedName("name_ru") val nameRu: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("count") val count: Int
)