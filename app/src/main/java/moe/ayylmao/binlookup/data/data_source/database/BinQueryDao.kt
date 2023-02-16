package moe.ayylmao.binlookup.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import moe.ayylmao.binlookup.domain.model.BinQuery

@Dao
interface BinQueryDao {
    @Query("SELECT * FROM BinQuery ORDER BY id DESC")
    fun getQueries(): Flow<List<BinQuery>>

    @Insert
    suspend fun insertQuery(binQuery: BinQuery)
}