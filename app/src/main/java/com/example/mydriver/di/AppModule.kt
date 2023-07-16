package com.example.mydriver.di

import com.example.mydriver.data.remote.repository.AuthRepositoryImpl
import com.example.mydriver.domain.repository.AuthRepository
import com.example.mydriver.domain.useCases.SignInUseCase
import com.example.mydriver.domain.useCases.SignUpUseCase
import com.example.mydriver.domain.useCases.UseCasesWrapper
import com.example.mydriver.presentation.signIn.SignInViewModel
import com.example.mydriver.presentation.signUp.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        FirebaseAuth.getInstance()
    }

    single {
        FirebaseDatabase.getInstance()
    }

    single {
        get<FirebaseDatabase>().reference
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        SignInUseCase(get())
    }

    single {
        SignUpUseCase(get())
    }

    single {
        UseCasesWrapper(get(), get())
    }

    viewModel {
        SignUpViewModel(get(), get())
    }

    viewModel {
        SignInViewModel(get(), get())
    }
}
