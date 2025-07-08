package com.finapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finapp.presentation.uiState.UiState
import com.finapp.domain.usecase.TransactionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransferViewModel(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {

    private val delayMillis: Long = 3000L

    private val _transferState = MutableLiveData<UiState<Unit>?>()
    val transferState: LiveData<UiState<Unit>?> = _transferState

    fun transfer(to: String, amount: Double) {
        _transferState.value = UiState.Loading

        viewModelScope.launch {
            delay(delayMillis)
            val result = transactionUseCase.fetchTransfer(to, amount)
            _transferState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Erro na transferência") }
            )
        }
    }

    fun resetState() {
        _transferState.value = null
    }
}