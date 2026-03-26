package uk.ac.tees.mad.resellit.ui.post_screen

import android.net.Uri

data class PostUiState(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val location: String = "",

    val images: List<Uri> = emptyList(),

    val isLoading: Boolean = false,
    val error: String? = null,
    val postSuccess: Boolean = false
){
    val canPost : Boolean
        get() = title.isNotEmpty() &&
                description.isNotEmpty() &&
                price.isNotEmpty() &&
                location.isNotEmpty() &&
                images.isNotEmpty()

    val isImagePicked : Boolean
        get() = images.isNotEmpty()
}

/**
 * this is ui state of post screen
 */