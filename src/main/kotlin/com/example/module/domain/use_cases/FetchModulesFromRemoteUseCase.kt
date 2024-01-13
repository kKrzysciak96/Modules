package com.example.module.domain.use_cases

import com.example.core.utils.ApiResult
import com.example.module.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.Flow

class FetchModulesFromRemoteUseCase(private val repository: ModuleRepository) {
    suspend operator fun invoke(): Flow<ApiResult> {
        return repository.fetchModulesFromRemote()
    }
}