package com.example.mydriver.domain.useCases

import com.example.mydriver.data.remote.models.UserRegistration
import com.example.mydriver.domain.repository.AuthRepository
import com.example.mydriver.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userRegistration: UserRegistration): Flow<Resource<AuthResult>> {
        return repository.registerUser(userRegistration.userEmail, userRegistration.userPassword)
    }
}
