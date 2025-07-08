package com.finapp.app.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.finapp.presentation.uiState.UiState
import com.finapp.domain.model.Account
import com.finapp.domain.model.Transaction
import com.finapp.domain.model.TransactionType
import com.finapp.domain.usecase.TransactionUseCase
import com.finapp.presentation.viewModels.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var useCase: TransactionUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk()
        viewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldEmitSuccessStateWhenFetchingData() = runTest {
        val account = Account("001", "Eric", 1000.0)
        val transactions = listOf(
            Transaction("t1", "2024-07-01", TransactionType.CREDIT, 100.0, "depósito")
        )

        coEvery { useCase.fetchBalance() } returns account
        coEvery { useCase.fetchTransactions() } returns transactions

        viewModel.balanceState.observeForever {}

        viewModel.loadHomeData()

        advanceUntilIdle()

        val balanceState = viewModel.balanceState.value

        assert(balanceState is UiState.Success)

        val balanceResult = (balanceState as UiState.Success).data
        assertEquals(account, balanceResult)
    }
}
