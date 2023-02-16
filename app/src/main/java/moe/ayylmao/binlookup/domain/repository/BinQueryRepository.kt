package moe.ayylmao.binlookup.domain.repository

import kotlinx.coroutines.flow.Flow
import moe.ayylmao.binlookup.domain.model.BinQuery

interface BinQueryRepository {
    fun getQueries(): Flow<List<BinQuery>>

    suspend fun insertQuery(query: BinQuery)
}