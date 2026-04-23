package uk.ac.tees.mad.resellit.util

import uk.ac.tees.mad.resellit.data.local.ListingEntity
import uk.ac.tees.mad.resellit.data.local.MyListingEntity
import uk.ac.tees.mad.resellit.data.model.InsertDto
import uk.ac.tees.mad.resellit.data.model.ListingDto


fun InsertDto.toMyListingEntity(): MyListingEntity {
        return MyListingEntity(
            listingId = this.listing_id,
            title = this.title,
            description = description,
            price = price,
            location = location,
            imageUrls = image_urls,
            userId = user_id ,
        )
    }


fun ListingDto.toListingEntity(): ListingEntity{
    return ListingEntity(
        listingId = listing_id,
        title = title,
        description = description,
        price = price,
        location = location,
        imageUrls = image_urls,
        userId = user_id,
        createdAt = created_at
    )
}

fun ListingDto.toMyListingEntity(): MyListingEntity{
    return MyListingEntity(
        listingId = listing_id,
        title = title,
        description = description,
        price = price,
        location = location,
        imageUrls = image_urls,
        userId = user_id
    )
}
