package moe.ayylmao.binlookup.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import moe.ayylmao.binlookup.domain.model.BinQuery

@Database(
    entities = [BinQuery::class],
    version = 1
)
abstract class BinQueryDatabase :RoomDatabase(){
    abstract val binQueryDao: BinQueryDao

    companion object {
        const val DATABASE_NAME = "queries_db"
    }
}