package com.finapp.app.ui.transfer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.finapp.presentation.uiState.UiState
import com.finapp.domain.usecase.TransactionUseCase
import com.finapp.presentation.viewModels.TransferViewModel
import com.finapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class TransferViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var useCase: TransactionUseCase
    private lateinit var viewModel: TransferViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk()
        viewModel = TransferViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldEmitSuccessWhenTransferSucceeds() = runTest {
        coEvery { useCase.fetchTransfer("destino", 100.0) } returns Result.success(Unit)

        viewModel.transfer("destino", 100.0)
        advanceUntilIdle()

        assert(viewModel.transferState.value is UiState.Success)
    }

    @Test
    fun shouldEmitErrorWhenTransferFails() = runTest {
        coEvery { useCase.fetchTransfer("destino", 100.0) } returns Result.failure(Exception("Erro"))

        viewModel.transfer("destino", 100.0)
        advanceUntilIdle()

        assert(viewModel.transferState.value is UiState.Error)
    }

    @Test
    fun resetStateShouldSetTransferStateToNull() {
        val viewModel = TransferViewModel(mockk(relaxed = true))

        viewModel.resetState()

        assert(viewModel.transferState.value == null)
    }
}
