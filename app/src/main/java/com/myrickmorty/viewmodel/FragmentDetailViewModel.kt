package com.myrickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrickmorty.core.ApiStatus
import com.myrickmorty.repository.Repository
import com.myrickmorty.utils.Constants.PAGE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentDetailViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _newStatus = MutableLiveData<ApiStatus>()
    val newStatus: LiveData<ApiStatus> = _newStatus

    fun getCharacters() {
        _newStatus.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                val characterList = repository.getAllCharacters(PAGE_INDEX)
                if (characterList.body()?.results.isNullOrEmpty()) {
                    _newStatus.value = ApiStatus.ERROR
                } else {
                    _newStatus.value = ApiStatus.DONE
                }
            } catch (e: Exception) {
                _newStatus.value = ApiStatus.ERROR
            }
        }
    }
}