package com.myrickmorty.viewmodel.fragment

import androidx.lifecycle.*
import com.myrickmorty.core.Resource
import com.myrickmorty.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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