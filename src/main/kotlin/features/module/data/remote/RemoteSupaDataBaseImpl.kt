package features.module.data.remote

import features.module.data.remote.model.SupabaseSpecificModule
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class RemoteSupaDataBaseImpl(private val client: SupabaseClient) :
    RemoteDataBase<SupabaseSpecificModule> {

    override suspend fun fetchModulesFromRemote(): List<SupabaseSpecificModule> {

        val dataResponse = client.postgrest["test"].select()
        val data = dataResponse.decodeList<SupabaseSpecificModule>()
        return data
    }

//    override suspend fun pushModulesToRemote(modules: List<SupabaseSpecificModule>) {
//        client.postgrest["test"].delete { SupabaseSpecificModule::reset isExact true }
//        val result = client.postgrest["test"].insert(modules)
//
//    }

}