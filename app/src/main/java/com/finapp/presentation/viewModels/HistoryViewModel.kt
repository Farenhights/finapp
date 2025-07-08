package com.finapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finapp.presentation.uiState.UiState
import com.finapp.domain.model.Transaction
import com.finapp.domain.usecase.TransactionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {

    private val delayMillis: Long = 3000L

    private val _transactionsState = MutableLiveData<UiState<List<Transaction>>>()
    val transactionsState: LiveData<UiState<List<Transaction>>> = _transactionsState

    fun loadTransactionHistory() {
        _transactionsState.value = UiState.Loading

        viewModelScope.launch {
            delay(delayMillis)
            try {
                val transactions = transactionUseCase.fetchTransactions()
                _transactionsState.value = UiState.Success(transactions)
            } catch (e: Exception) {
                _transactionsState.value = UiState.Error("Erro ao carregar histórico")
            }
        }
    }
}
