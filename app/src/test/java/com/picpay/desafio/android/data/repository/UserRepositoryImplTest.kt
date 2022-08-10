package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.state.ResultState
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UserRepositoryImplTest {

    private val picPayService: PicPayService = Mockito.mock(PicPayService::class.java)

    private val repository: UserRepository = UserRepositoryImpl(picPayService)

    @Test
    fun shouldReturnAllUsers() = runBlocking {
        val users = listOf(User("URL", "NAME", 1, "USERNAME"))
        Mockito.`when`(picPayService.getUsers()).thenReturn(users)

        val resultState = repository?.getAllUsers()

        Assert.assertTrue(resultState is ResultState.Success)
        Assert.assertTrue((resultState as ResultState.Success).data.size == 1)
    }

    @Test
    fun shouldReturnError() = runBlocking {
        Mockito.`when`(picPayService.getUsers()).thenThrow(RuntimeException::class.java)

        val resultState = repository?.getAllUsers()

        Assert.assertTrue(resultState is ResultState.Error)
    }
}