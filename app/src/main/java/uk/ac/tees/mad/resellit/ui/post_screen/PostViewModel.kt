package uk.ac.tees.mad.resellit.ui.post_screen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PostViewModel(application: Application) :
    AndroidViewModel(application) {
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
