package moe.ayylmao.binlookup.data.repository

import kotlinx.coroutines.flow.Flow
import moe.ayylmao.binlookup.data.data_source.database.BinQueryDao
import moe.ayylmao.binlookup.domain.model.BinQuery
import moe.ayylmao.binlookup.domain.repository.BinQueryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinQueryRepositoryImpl @Inject constructor(private val binQueryDao: BinQueryDao) :
    BinQueryRepository {
    override fun getQueries(): Flow<List<BinQuery>> {
        return binQueryDao.getQueries()
    }

    override suspend fun insertQuery(query: BinQuery) {
        binQueryDao.insertQuery(query)
    }
}