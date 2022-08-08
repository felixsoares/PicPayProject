package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.state.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getAllUsers(): ResultState<List<User>>
}

class UserRepositoryImpl(
    private val picPayService: PicPayService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    override suspend fun getAllUsers() = withContext(dispatcher) {
        return@withContext try {
            val users = picPayService.getUsers()
            ResultState.Success(users)
        } catch (e: Exception) {
            ResultState.Error
        }
    }

}