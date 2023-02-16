package moe.ayylmao.binlookup.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BinQuery(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bin: String,
    val bankName: String
)
