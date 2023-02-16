package moe.ayylmao.binlookup.domain.repository

import moe.ayylmao.binlookup.domain.model.binlist.BinlistResponse
import moe.ayylmao.binlookup.util.NetworkResult

interface BinlistRepository {
    suspend fun getBankInfo(bin: String): NetworkResult<BinlistResponse>
}