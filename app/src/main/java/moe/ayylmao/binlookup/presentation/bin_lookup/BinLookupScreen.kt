package moe.ayylmao.binlookup.presentation.bin_lookup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import moe.ayylmao.binlookup.R
import moe.ayylmao.binlookup.presentation.bin_lookup.components.BinQueryHistoryItem
import moe.ayylmao.binlookup.presentation.bin_lookup.components.BinQueryResult


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BinLookupScreen(
    viewModel: BinLookupViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val queries by viewModel.queries.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current

    fun makeQuery() {
        if (state.inputQueryText.isBlank()) {
            return
        }

        keyboardController?.hide()
        viewModel.onEvent(BinLookupEvent.MakeQuery)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BinLookupViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(modifier = Modifier.padding(16.dp), scaffoldState = scaffoldState) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Text(
                    text = stringResource(R.string.welcome_text),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.inputQueryText,
                        onValueChange = {
                            val filtered = it.filter { symbol -> symbol.isDigit() }
                            viewModel.onEvent(BinLookupEvent.EnteredQuery(filtered))
                        },
                        placeholder = { Text(text = stringResource(R.string.enter_bin)) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { makeQuery() }
                        )
                    )
                    IconButton(onClick = { makeQuery() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = stringResource(
                                R.string.search
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (state.queryResult != null) {
                    BinQueryResult(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        binlistResponse = state.queryResult
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.query_history),
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Empty history placeholder
                if (!state.queries.any()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.empty),
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
            }

            items(queries) { query ->
                BinQueryHistoryItem(binQuery = query,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            viewModel.onEvent(BinLookupEvent.EnteredQuery(query.bin))
                        })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}