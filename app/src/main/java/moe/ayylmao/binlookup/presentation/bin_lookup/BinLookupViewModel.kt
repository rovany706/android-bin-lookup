package moe.ayylmao.binlookup.presentation.bin_lookup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.ayylmao.binlookup.domain.model.BinQuery
import moe.ayylmao.binlookup.domain.repository.BinQueryRepository
import moe.ayylmao.binlookup.domain.repository.BinlistRepository
import moe.ayylmao.binlookup.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class BinLookupViewModel @Inject constructor(
    private val binQueryRepository: BinQueryRepository,
    private val binlistRepository: BinlistRepository
) : ViewModel() {
    private val _state = mutableStateOf(BinLookupState())
    val state: State<BinLookupState> = _state

    val queries = binQueryRepository.getQueries()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getQueriesJob: Job? = null

    init {
        getQueries()
    }

    private fun getQueries() {
        getQueriesJob?.cancel()
        getQueriesJob = binQueryRepository.getQueries()
            .onEach { queries ->
                _state.value = state.value.copy(
                    queries = queries,
                    inputQueryText = state.value.inputQueryText
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: BinLookupEvent) {
        when (event) {
            is BinLookupEvent.EnteredQuery -> {
                _state.value = _state.value.copy(
                    queries = state.value.queries,
                    inputQueryText = event.query
                )
            }
            is BinLookupEvent.MakeQuery -> {
                viewModelScope.launch {
                    when (val result = binlistRepository.getBankInfo(state.value.inputQueryText)) {
                        is NetworkResult.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(result.message ?: "Error"))
                        }
                        is NetworkResult.Success -> {
                            if (result.data?.bank == null) {
                                _eventFlow.emit(UiEvent.ShowSnackBar("Bank not found"))
                                return@launch
                            }

                            val bankName = result.data.bank.name ?: "No Name"

                            binQueryRepository.insertQuery(
                                BinQuery(
                                    bin = state.value.inputQueryText,
                                    bankName = bankName
                                )
                            )

                            _state.value = state.value.copy(
                                queries = state.value.queries,
                                inputQueryText = state.value.inputQueryText,
                                queryResult = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}