package uk.ac.tees.mad.resellit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListingDto(
    val listing_id: String,
    val title: String,
    val description: String,
    val price: String,
    val location: String,
    val image_urls: List<String>,
    val user_id: String ,
    val created_at : String
)




/**
 * this data class is responsible for posting the data wrapped into this object
 */