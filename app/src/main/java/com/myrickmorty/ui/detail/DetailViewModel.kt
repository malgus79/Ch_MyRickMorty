package com.myrickmorty.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrickmorty.data.RepositoryImpl
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.domain.RepositoryCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: RepositoryCharacter) : ViewModel() {

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