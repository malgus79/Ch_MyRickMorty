package com.myrickmorty.viewmodel.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.myrickmorty.core.Resource
import com.myrickmorty.domain.Repository
import com.myrickmorty.model.data.RickMorty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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










