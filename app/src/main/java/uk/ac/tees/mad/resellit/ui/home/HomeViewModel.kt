package uk.ac.tees.mad.resellit.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val listingRepository: ListingRepository =
        (application as ResellItApp).container.listingRepository
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        observeListings()
        refreshFeed()
        viewModelScope.launch {
            listingRepository.observeRealtime()
        }
    }

    fun loadMore() {

        if (_homeUiState.value.isLoadingMore) return

        viewModelScope.launch {

           _homeUiState.update { it.copy(isLoadingMore = true) }

            listingRepository.loadOlder()

            _homeUiState.update { it.copy(isLoadingMore = false) }

        }
    }

    fun refreshFeed(){
        viewModelScope.launch {
            try {
                _homeUiState.update { it.copy(isRefreshing = true) }
                listingRepository.refreshFeed()
            } catch (e: Exception) {
                _homeUiState.update { it.copy(error = e.message) }
            } finally {
                _homeUiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
  private fun observeListings() {
        viewModelScope.launch {
            listingRepository
                .observeListings()
                .collect { listing ->
                    _homeUiState.update {
                        it.copy(
                            listings = listing ,
                            error = null
                        )
                    }
                }
        }
    }
}