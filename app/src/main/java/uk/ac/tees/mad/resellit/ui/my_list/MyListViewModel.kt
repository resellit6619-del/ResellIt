package uk.ac.tees.mad.resellit.ui.my_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository

class MyListViewModel(application: Application) : AndroidViewModel(application) {
    val listingRepository: ListingRepository =
        (application as ResellItApp).container.listingRepository
    private val _myListUiState = MutableStateFlow(MyListUiState())
    val muListUiState = _myListUiState.asStateFlow()

    init {
        fetchMyListing()
    }

    private fun fetchMyListing() {
        _myListUiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            listingRepository
                .getUserListings()
                .onFailure { error ->
                    _myListUiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
                .onSuccess { success ->
                    success.collect { list ->
                        _myListUiState.update {
                            it.copy(
                                isLoading = false,
                                listings = list
                            )
                        }
                    }
                }
        }
    }

    fun onDeleteClick(listingId: String) {
        viewModelScope.launch {
            _myListUiState.update {
                it.copy(isLoading = true)
            }
            listingRepository
                .deleteListing(listingId)
                .onSuccess {
                    _myListUiState.update {
                        it.copy(
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _myListUiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ,
                        )
                    }
                }
        }
    }
}
