package com.example.module.data.local

import com.example.ModuleEntity
import kotlinx.coroutines.flow.Flow

interface ModuleDataSource {
    suspend fun getModuleById(id: String): ModuleEntity?
    fun getModules(): Flow<List<ModuleEntity>>
    suspend fun addModule(module: ModuleEntity)
    suspend fun deleteModuleById(id: String)
    suspend fun dropDatabase()

}