package com.finapp.app.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.finapp.domain.model.Transaction
import com.finapp.domain.model.TransactionType
import com.finapp.domain.usecase.TransactionUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.finapp.presentation.uiState.UiState.*
import com.finapp.presentation.viewModels.HistoryViewModel

@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var useCase: TransactionUseCase
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk()
        viewModel = HistoryViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldEmitSuccessWhenFetchTransactionsSucceeds() = runTest {
        val transactions = listOf(
            Transaction(
                "t1",
                "2024-07-01",
                TransactionType.CREDIT,
                100.0,
                "depósito"
            )
        )
        coEvery { useCase.fetchTransactions() } returns transactions

        viewModel.transactionsState.observeForever {}

        viewModel.loadTransactionHistory()
        advanceUntilIdle()

        val result = viewModel.transactionsState.value
        assertTrue(result is Success)
        assertEquals(transactions, (result as Success).data)
    }

    @Test
    fun shouldEmitErrorWhenFetchTransactionsThrowsException() = runTest {
        coEvery { useCase.fetchTransactions() } throws RuntimeException("Erro")

        viewModel.transactionsState.observeForever {}

        viewModel.loadTransactionHistory()
        advanceUntilIdle()

        val result = viewModel.transactionsState.value
        assertTrue(result is Error)
        assertEquals("Erro ao carregar histórico", (result as Error).message)
    }

    @Test
    fun shouldEmitLoadingFirstWhenFetchingTransactions() = runTest {
        coEvery { useCase.fetchTransactions() } returns emptyList()

        viewModel.transactionsState.observeForever {}

        viewModel.loadTransactionHistory()

        assertTrue(viewModel.transactionsState.value is Loading)

        advanceUntilIdle()
    }
}
