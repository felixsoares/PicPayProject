package com.picpay.desafio.android.data.state

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    object Error : ResultState<Nothing>()
}