package moe.ayylmao.binlookup.presentation.bin_lookup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.ayylmao.binlookup.domain.model.BinQuery

@Composable
fun BinQueryHistoryItem(
    modifier: Modifier,
    binQuery: BinQuery
) {
    Card(
        modifier = modifier,
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(text = binQuery.bankName, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "BIN: ${binQuery.bin}", style = MaterialTheme.typography.caption)
        }
    }
}