package uz.fayzullo.oshxona.data.remote.model

import com.google.gson.annotations.SerializedName


data class RecipeShortDto(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String?,
    @SerializedName("lang") val lang: String?,
    @SerializedName("title") val title: String,
    @SerializedName("primary_category") val primaryCategory: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("video_url") val videoUrl: String?,
    @SerializedName("has_video") val hasVideo: Boolean?
)