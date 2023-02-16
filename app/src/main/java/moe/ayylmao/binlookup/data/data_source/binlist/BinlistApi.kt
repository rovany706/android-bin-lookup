package moe.ayylmao.binlookup.data.data_source.binlist

import moe.ayylmao.binlookup.domain.model.binlist.BinlistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BinlistApi {
    @GET("/{bin}")
    @Headers(
        "Accept-Version: 3"
    )
    suspend fun getBankInfo(@Path("bin") bin: String) : Response<BinlistResponse>

    companion object {
        const val BASE_URL = "https://lookup.binlist.net"
    }
}