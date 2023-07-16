package com.example.mydriver.domain.useCases

import com.example.mydriver.domain.repository.AuthRepository
import com.example.mydriver.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<AuthResult>> {
        return repository.loginUser(email, password)
    }
}
