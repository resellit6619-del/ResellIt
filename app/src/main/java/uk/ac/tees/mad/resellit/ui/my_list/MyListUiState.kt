package uk.ac.tees.mad.resellit.ui.my_list

import uk.ac.tees.mad.resellit.domain.model.DomainListing

data class MyListUiState(
    val isLoading : Boolean = false ,
    val error : String? = null ,
    val listings : List<DomainListing> = emptyList()
)
