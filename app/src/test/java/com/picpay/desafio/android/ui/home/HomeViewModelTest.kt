package com.picpay.desafio.android.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.state.ResultState
import com.picpay.desafio.android.data.state.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito
import java.lang.Exception

class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val userRepository: UserRepository = Mockito.mock(UserRepository::class.java)

    private val viewModel: HomeViewModel = HomeViewModel(userRepository)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun shouldReturnAllUsers() = runBlocking {
        val mockObserver = Mockito.mock(Observer::class.java)

        viewModel.viewState.observeForever(mockObserver as Observer<in ViewState<List<User>>>)

        val users = listOf(User("URL", "NAME", 1, "USERNAME"))
        val resultState = ResultState.Success(users)

        Mockito.`when`(userRepository.getAllUsers()).thenReturn(resultState)

        viewModel.getAllUsers()

        val state = viewModel.viewState.value

        Assert.assertTrue(state is ViewState.ContentLoaded)
        Assert.assertTrue((state as ViewState.ContentLoaded).data.size == 1)

        // verificar se o mockObserver recebeu o evento ViewSate.Loading
        // verificar se o mockObserver recebeu o evento ViewSate.ContentLoaded
        // verificar se o mockObserver nunca recebeu o evento ViewSate.ContentFailure
    }

    @Test
    fun shouldReturnError() = runBlocking {
        val resultState = ResultState.Error

        Mockito.`when`(userRepository.getAllUsers()).thenReturn(resultState)

        viewModel.getAllUsers()

        val state = viewModel.viewState.value

        Assert.assertTrue(state is ViewState.ContentFailure)

        // verificar se o mockObserver recebeu o evento ViewSate.Loading
        // verificar se o mockObserver recebeu o evento ViewSate.ContentFailure
        // verificar se o mockObserver nunca recebeu o evento ViewSate.ContentLoaded
    }
}