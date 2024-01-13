package com.example.module.domain.use_cases

import com.example.module.domain.repository.ModuleRepository

class DropDataBase(private val repository: ModuleRepository) {

    suspend operator fun invoke() {
        repository.dropDatabase()
    }
}