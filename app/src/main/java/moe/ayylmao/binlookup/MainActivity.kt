package moe.ayylmao.binlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import moe.ayylmao.binlookup.presentation.bin_lookup.BinLookupScreen
import moe.ayylmao.binlookup.ui.theme.BinLookupTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BinLookupTheme {
                Surface(color = MaterialTheme.colors.background) {
                    BinLookupScreen()
                }
            }
        }
    }
}