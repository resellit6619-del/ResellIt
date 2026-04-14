package uk.ac.tees.mad.resellit.util

import uk.ac.tees.mad.resellit.data.local.ListingEntity
import uk.ac.tees.mad.resellit.domain.model.DomainListing


fun ListingEntity.toDomainListing(): DomainListing {
    return DomainListing(
        listingId = listingId,
        title = title,
        description = description,
        price = price,
        location = location,
        imageUrls = imageUrls,
        userId = userId
    )
}