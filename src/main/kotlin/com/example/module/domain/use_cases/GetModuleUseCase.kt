package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository
import java.util.*

class GetModuleUseCase(private val repository: ModuleRepository) {

    suspend operator fun invoke(id: UUID): ModuleDomain {
        return repository.getModule(id)
    }
}