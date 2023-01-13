package com.myrickmorty.model.data

import android.os.Parcelable
import com.myrickmorty.model.local.FavoriteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class RickMorty(
    val id: Int? = -1,
    val created: String? = "",
    //val episode: List<String>?,
    val gender: String? = "",
    val image: String? = "",
    val name: String? = "",
    val species: String? = "",
    val status: String? = "",
    val type: String? = "",
    val url: String? = "",
) : Parcelable

data class RickMortyList(val results: List<RickMorty> = listOf())

fun RickMorty.asFavoriteEntity(): FavoriteEntity = FavoriteEntity(
    this.id,
    this.created,
    this.gender,
    this.image,
    this.name,
    this.species,
    this.status,
    this.type,
    this.url,
)