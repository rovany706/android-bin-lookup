package moe.ayylmao.binlookup.presentation.bin_lookup

import moe.ayylmao.binlookup.domain.model.BinQuery
import moe.ayylmao.binlookup.domain.model.binlist.BinlistResponse

data class BinLookupState(
    val queries: List<BinQuery> = emptyList(),
    val queryResult: BinlistResponse? = null
)