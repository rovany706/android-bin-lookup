package moe.ayylmao.binlookup.data.repository

import moe.ayylmao.binlookup.data.data_source.binlist.BinlistApi
import moe.ayylmao.binlookup.domain.model.binlist.BinlistResponse
import moe.ayylmao.binlookup.domain.repository.BinlistRepository
import moe.ayylmao.binlookup.util.NetworkResult
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinlistRepositoryImpl @Inject constructor(private val binlistApi: BinlistApi) :
    BinlistRepository {
    override suspend fun getBankInfo(bin: String): NetworkResult<BinlistResponse> {
        return try {
            val response = binlistApi.getBankInfo(bin)

            if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!)
            } else if (response.code() == 404) {
                NetworkResult.Error("BIN not found")
            } else {
                NetworkResult.Error("Error when fetching data (${response.code()})")
            }
        } catch (e: HttpException) {
            NetworkResult.Error("Error when fetching data")
        }
    }
}