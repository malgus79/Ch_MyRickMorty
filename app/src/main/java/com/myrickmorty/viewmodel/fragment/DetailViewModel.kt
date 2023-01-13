package com.myrickmorty.viewmodel.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrickmorty.domain.Repository
import com.myrickmorty.model.data.RickMorty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun saveOrDeleteFavoriteCharacter(character: RickMorty) {
        viewModelScope.launch {
            if (repository.isCharacterFavorite(character)) {
                repository.deleteFavoriteCharacter(character)
            } else {
                repository.saveFavoriteCharacter(character)
            }
        }
    }

    suspend fun isCharacterFavorite(character: RickMorty): Boolean =
        repository.isCharacterFavorite(character)
}