package com.capella.viewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capella.models.Quote
import com.capella.network.ZenQuotesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// includes a loading state for the quote while being fetched
data class QuoteUiState(
    val isLoading: Boolean = false,
    val quote: Quote? = null,
    val error: String? = null
)

class QuoteViewModel : ViewModel() {
    private val api = ZenQuotesApi.create()

    private val _uiState = MutableStateFlow(QuoteUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = QuoteUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val list = api.getRandomQuote()
                val dto = list.firstOrNull()
                val domain = dto?.toDomain()
                if (domain != null) {
                    _uiState.value = QuoteUiState(isLoading = false, quote = domain)
                } else {
                    _uiState.value = QuoteUiState(isLoading = false, error = "No quote returned")
                }
            } catch (e: Exception) {
                _uiState.value = QuoteUiState(isLoading = false, error = e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
