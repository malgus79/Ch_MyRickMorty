package com.myrickmorty.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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