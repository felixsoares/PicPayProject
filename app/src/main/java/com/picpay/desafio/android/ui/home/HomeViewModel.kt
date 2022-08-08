package com.picpay.desafio.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.state.ResultState
import com.picpay.desafio.android.data.state.ViewState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<User>>>()
    val viewState: LiveData<ViewState<List<User>>>
        get() = _viewState

    fun getAllUsers() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            when (val state = userRepository.getAllUsers()) {
                is ResultState.Success -> {
                    _viewState.value = ViewState.ContentLoaded(state.data)
                }
                is ResultState.Error -> {
                    _viewState.value = ViewState.ContentFailure
                }
            }
        }
    }

}