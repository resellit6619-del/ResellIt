package uk.ac.tees.mad.resellit.util

import uk.ac.tees.mad.resellit.data.local.ListingEntity
import uk.ac.tees.mad.resellit.data.model.ListingDto
import uk.ac.tees.mad.resellit.domain.model.DomainListing



    fun ListingDto.toListingEntity(): ListingEntity {
        return ListingEntity(
            listingId = this.listing_id,
            title = this.title,
            description = description,
            price = price,
            location = location,
            imageUrls = image_urls,
            userId = user_id
        )
    }
