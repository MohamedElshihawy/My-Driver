package com.example.mydriver.domain.repository

import com.example.mydriver.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    suspend fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}
