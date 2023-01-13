package com.myrickmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.myrickmorty.core.connectivity.ConnectivityNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(connectivityNetwork: ConnectivityNetwork) :
    ViewModel() {

    val isConnected: Flow<Boolean> = connectivityNetwork.isConnected

}