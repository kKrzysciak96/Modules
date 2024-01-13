package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository


class DeleteModulesUseCase(private val repository: ModuleRepository) {

    suspend operator fun invoke(module: List<ModuleDomain>) {
        repository.deleteModules(module)
    }
}