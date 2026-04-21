package uk.ac.tees.mad.resellit.ui.detail

import uk.ac.tees.mad.resellit.domain.model.DomainListing

data class DetailUiState(
    val isLoading : Boolean = false ,
    val error: String ? = null ,
    val entity : DomainListing ? = null
)
