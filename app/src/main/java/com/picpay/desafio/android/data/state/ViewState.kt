package com.picpay.desafio.android.data.state

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class ContentLoaded<out T>(val data: T) : ViewState<T>()
    object ContentFailure : ViewState<Nothing>()
}