package moe.ayylmao.binlookup.presentation.bin_lookup

sealed class BinLookupEvent {
    data class EnteredQuery(val query: String): BinLookupEvent()
    object MakeQuery: BinLookupEvent()
}