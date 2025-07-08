package com.finapp.di.modules

import com.finapp.presentation.viewModels.HistoryViewModel
import com.finapp.presentation.viewModels.HomeViewModel
import com.finapp.presentation.viewModels.TransferViewModel
import com.finapp.data.remote.MockInterceptor
import com.finapp.data.remote.TransactionApi
import com.finapp.data.repository.TransactionRepositoryImpl
import com.finapp.domain.repository.TransactionRepository
import com.finapp.domain.usecase.TransactionUseCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { HomeViewModel(transactionUseCase = get()) }
    viewModel { TransferViewModel(transactionUseCase = get()) }
    viewModel { HistoryViewModel(transactionUseCase = get()) }
}

val useCaseModule = module {
    factory { TransactionUseCase(get()) }
}

val repositoryModule = module {
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}

val networkModule = module {
    single<Interceptor> { MockInterceptor(androidContext()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://mock.api/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionApi::class.java)
    }
}