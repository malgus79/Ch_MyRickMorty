package com.myrickmorty.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.model.data.RickMortyList
import retrofit2.Response

@Entity(tableName = "character_entity")
data class RickMortyEntity(
    @PrimaryKey val id: Int? = -1,
    @ColumnInfo(name = "created") val created: String? = "",
    @ColumnInfo(name = "gender") val gender: String? = "",
    @ColumnInfo(name = "image") val image: String? = "",
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "species") val species: String? = "",
    @ColumnInfo(name = "status") val status: String? = "",
    @ColumnInfo(name = "type") val type: String? = "",
    @ColumnInfo(name = "url") val url: String? = ""
)

fun RickMorty.toRickMortyEntity(): RickMortyEntity = RickMortyEntity(
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

fun List<RickMortyEntity>.toRickMortyList(): RickMortyList {
    val resultList = mutableListOf<RickMorty>()
    this.forEach { rickMortyEntity ->
        resultList.add(rickMortyEntity.toRickMorty())
    }
    return  RickMortyList(resultList)
}

fun RickMortyEntity.toRickMorty(): RickMorty = RickMorty(
    this.created,
    this.gender,
    this.id,
    this.image,
    this.name,
    this.species,
    this.status,
    this.type,
    this.url
)
