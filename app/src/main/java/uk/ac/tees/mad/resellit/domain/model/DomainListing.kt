package uk.ac.tees.mad.resellit.domain.model

data class DomainListing (
    val listingId: String,
    val title: String,
    val description: String,
    val price: String,
    val location: String,
    val imageUrls: List<String>,
    val userId: String
)