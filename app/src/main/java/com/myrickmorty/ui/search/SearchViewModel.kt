package com.myrickmorty.ui.search

import androidx.lifecycle.*
import com.myrickmorty.core.Resource
import com.myrickmorty.data.RepositoryImpl
import com.myrickmorty.domain.RepositoryCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: RepositoryCharacter) : ViewModel() {

    private val mutableCharacterSearched = MutableLiveData<String>()

    fun setCharacterSearched(queryCharacter: String) {
        mutableCharacterSearched.value = queryCharacter
    }

    val fetchCharacterList = mutableCharacterSearched.distinctUntilChanged().switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                repository.getCharacterByName(it).collectLatest {
                    emit(it)
                }

            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}