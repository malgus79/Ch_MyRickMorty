package com.myrickmorty.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.myrickmorty.core.Resource
import com.myrickmorty.data.RepositoryImpl
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.domain.RepositoryCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: RepositoryCharacter) : ViewModel() {

    fun fetchFavoriteCharacters() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.getFavoriteCharacters()))
        } catch (e:Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun deleteFavoriteCharacter(character: RickMorty) {
        viewModelScope.launch {
            repository.deleteFavoriteCharacter(character)
        }
    }
}










