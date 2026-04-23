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

    suspend fun getUserListings() : Result<Flow<List<DomainListing>>>

    suspend fun deleteListing(listingId : String ): Result<Unit>

    suspend fun deleteAllListing() : Result<Unit>

    suspend fun observeListings(): Flow<List<DomainListing>>

    suspend fun refreshFeed(): Result<Unit>

    suspend fun loadOlder()

    suspend fun observeRealtime()

    suspend fun loadOnLogin(): Result<Unit>

    suspend fun deleteOnLogout(): Result<Unit>

    suspend fun fetchByListingId(listingId : String) : Result<DomainListing>
}