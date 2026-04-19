package uk.ac.tees.mad.resellit.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.resellit.domain.model.DomainListing

interface ListingRepository {

    suspend fun createListing(
        price : String ,
        title : String ,
        description : String ,
        location : String ,
        imageUrls : List<Uri>
    ): Result<Unit>

    suspend fun getAllListings()

    suspend fun getUserListings() : Result<Flow<List<DomainListing>>>

    suspend fun deleteListing(listingId : String ): Result<Unit>

    suspend fun deleteAllListing() : Result<Unit>

}

/**
 * createListing() -- responsible for creating listing
 * getAllListings() -- responsible for getting all listings
 * getUserListings() -- responsible for getting user listings
 * deleteListing() -- responsible for deleting listing
 */