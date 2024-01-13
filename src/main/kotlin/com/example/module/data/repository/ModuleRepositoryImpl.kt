package com.example.module.data.repository

import com.example.core.extensions.toModuleDomain
import com.example.core.extensions.toModuleEntity
import com.example.core.utils.ApiResult
import com.example.module.data.local.ModuleDataSource
import com.example.module.data.remote.RemoteDatabase
import com.example.module.data.remote.model.SupabaseSpecificModule
import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*

class ModuleRepositoryImpl(
    private val remoteDataBase: RemoteDatabase<SupabaseSpecificModule>,
    private val localDataBase: ModuleDataSource,
) : ModuleRepository {

    //        override suspend fun getModules(): Flow<ApiResult> {
//        return flow {
//            emit(ApiResult.Loading)
//            try {
//                val remoteData = remoteDataBase.fetchModulesFromRemote().map { it.toModule() }
//                emit(ApiResult.Success(remoteData))
//            } catch (e: Exception) {
//                emit(ApiResult.Error(e.message))
//            }
//        }
//    }
    override suspend fun editModule(module: ModuleDomain) {
        localDataBase.addModule(module.toModuleEntity())
    }

    override suspend fun addModule(module: ModuleDomain) {
        localDataBase.addModule(module.toModuleEntity())
    }

    override suspend fun addModules(modules: List<ModuleDomain>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteModule(module: ModuleDomain) {
        localDataBase.deleteModuleById(module.id.toString())
    }

    override suspend fun deleteModules(modules: List<ModuleDomain>) {
        TODO("Not yet implemented")
    }

    override fun getModules(): Flow<List<ModuleDomain>> {
        return localDataBase.getModules().map { list ->
            list.map { it.toModuleDomain() }
        }
    }

    override suspend fun getModule(id: UUID): ModuleDomain {
        return localDataBase.getModuleById(id.toString())!!.toModuleDomain()
    }

    override suspend fun dropDatabase() {
        localDataBase.dropDatabase()
    }

    override suspend fun pushModulesToRemote(): Flow<ApiResult> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchModulesFromRemote(): Flow<ApiResult> {
        return flow {
            emit(ApiResult.Loading)
            try {
                val remoteData = remoteDataBase.fetchModulesFromRemote().map { it.toModule() }
                emit(ApiResult.Success(remoteData))
            } catch (e: Exception) {
                emit(ApiResult.Error(e.message))
            }
        }
    }
}