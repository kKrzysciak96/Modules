package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.Flow


class GetModulesUseCase(private val repository: ModuleRepository) {

    operator fun invoke(): Flow<List<ModuleDomain>> {
        return repository.getModules()
    }
}