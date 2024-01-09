package features.module.data.repository

import core.utils.ApiResult
import features.module.data.remote.RemoteDataBase
import features.module.data.remote.model.SupabaseSpecificModule
import features.module.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ModuleRepositoryImpl(private val remoteDataBase: RemoteDataBase<SupabaseSpecificModule>) : ModuleRepository {

    override suspend fun getModules(): Flow<ApiResult> {
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