package uk.ac.tees.mad.resellit.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val listingRepository: ListingRepository =
        (application as ResellItApp).container.listingRepository
    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    fun fetchByListingId(listingId: String) {
        viewModelScope.launch {
            _detailUiState.update { it.copy(isLoading = true) }

            listingRepository
                .fetchByListingId(listingId)
                .onFailure { error ->
                    _detailUiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message,
                            entity = null
                        )
                    }
                }
                .onSuccess { entity ->
                    _detailUiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            entity = entity
                        )
                    }
                }
        }
    }
}