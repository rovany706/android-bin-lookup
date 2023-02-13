package moe.ayylmao.binlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import moe.ayylmao.binlookup.ui.theme.BinLookupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BinLookupTheme {

            }
        }
    }
}