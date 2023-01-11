package com.myrickmorty.model.data

import android.os.Parcelable
import com.myrickmorty.model.local.FavoriteEntity
import com.myrickmorty.model.local.RickMortyEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class RickMorty(
    val id: Int?,
    val created: String?,
    //val episode: List<String>?,
    val gender: String?,
    val image: String?,
    val name: String?,
    val species: String?,
    val status: String?,
    val type: String?,
    val url: String?,
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

fun RickMorty.asRickMortyEntity(): RickMortyEntity = RickMortyEntity(
    this.id,
    this.created,
    this.gender,
    this.image,
    this.name,
    this.species,
    this.status,
    this.type,
    this.url
)


/////////////////////////////////////
//
//fun List<RickMorty>.asRickMortyList(): RickMortyList {
//    val resultList = mutableListOf<RickMorty>()
//    this.forEach { rickMorty ->
//        resultList.add(rickMorty.toRickMorty())
//    }
//    return RickMortyList(resultList)
//}
//
//fun RickMorty.toRickMorty(): RickMorty = RickMorty(
//    this.id,
//    this.created,
//    this.gender,
//    this.image,
//    this.name,
//    this.species,
//    this.status,
//    this.type,
//    this.url
//)
