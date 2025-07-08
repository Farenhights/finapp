package com.finapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finapp.presentation.uiState.UiState
import com.finapp.domain.model.Account
import com.finapp.domain.usecase.TransactionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {

    private val delayMillis: Long = 3000L

    private val _balanceState = MutableLiveData<UiState<Account>>()
    val balanceState: LiveData<UiState<Account>> = _balanceState

    fun loadHomeData() {
        _balanceState.value = UiState.Loading

        viewModelScope.launch {
            delay(delayMillis)
            try {
                val balance = transactionUseCase.fetchBalance()
                _balanceState.value = UiState.Success(balance)
            } catch (e: Exception) {
                _balanceState.value = UiState.Error("Erro ao carregar saldo")
            }
        }
    }
}
