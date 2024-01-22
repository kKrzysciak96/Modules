package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository


class GetModulesByNameUseCase(private val repository: ModuleRepository) {

    suspend operator fun invoke(name: String): List<ModuleDomain> {
        return repository.getModulesByName(name)
    }
}