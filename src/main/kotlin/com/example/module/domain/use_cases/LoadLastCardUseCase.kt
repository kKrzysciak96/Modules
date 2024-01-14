package com.example.module.domain.use_cases

import com.example.module.domain.repository.ModuleRepository


class LoadLastCardUseCase(private val repository: ModuleRepository) {
    suspend operator fun invoke(): Int {
        return repository.loadLastCard()
    }
}