package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository


class DeleteModuleUseCase(private val repository: ModuleRepository) {

    suspend operator fun invoke(module: ModuleDomain) {
        repository.deleteModule(module)
    }
}