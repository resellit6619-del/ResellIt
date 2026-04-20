package uk.ac.tees.mad.resellit.ui.home

import uk.ac.tees.mad.resellit.domain.model.DomainListing


data class HomeUiState(
    val isLoading : Boolean = false,
    val isLoadingMore : Boolean = false ,
    val listings : List<DomainListing> = emptyList(),
    val error : String? = null,
)