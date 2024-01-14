package com.example.module.domain.use_cases

import com.example.module.domain.repository.ModuleRepository

class SaveLastCardUseCase(private val repository: ModuleRepository) {
    suspend operator fun invoke(page: Int) {
        repository.saveLastCard(page)
    }
}