package uk.ac.tees.mad.resellit.ui.post_screen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository

class PostViewModel(application: Application) :
    AndroidViewModel(application) {

        private val listingRepository: ListingRepository =
            (application as ResellItApp).container.listingRepository
        private val _postUiState = MutableStateFlow(PostUiState())
    val postUiState = _postUiState.asStateFlow()


    fun onTitleChange(newTitle: String) {
        _postUiState.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun onDescriptionChange(newDescription: String) {
        _postUiState.update {
            it.copy(
                description = newDescription
            )
        }
    }

    fun onPriceChange(newPrice: String) {
        _postUiState.update {
            it.copy(
                price = newPrice
            )
        }
    }

    fun onLocationChange(newLocation: String) {
        _postUiState.update {
            it.copy(
                location = newLocation
            )
        }
    }

    fun onSelectedImages(newImages: List<Uri>) {
        _postUiState.update {
            it.copy(
                images = newImages
            )
        }
    }

    fun onPostClick() {

        val state = _postUiState.value

        _postUiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            listingRepository
                .createListing(
                    price = state.price ,
                    title = state.title ,
                    description = state.description,
                    location = state.location,
                    imageUrls =  state.images
                )
                .onSuccess {
                    _postUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = null ,
                            postSuccess = true
                        )
                    }
                }
                .onFailure { error->
                    _postUiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ,
                            postSuccess = false
                        )
                    }
                }
        }
    }

    fun reset(){
        _postUiState.update {
            it.copy(
                isLoading = false ,
                error = null ,
                postSuccess = false ,
                title = "",
                description = "",
                price = "",
                location = "",
                images = emptyList()
            )
        }
    }
}


/**
 * onTitleChange() for managing title string
 * onDescriptionChange() for managing description string
 * onPriceChange() for managing price string
 * onLocationChange() for managing location string
 * onSelectedImages() for managing selected images
 * onPostClick() for managing post click
 */
