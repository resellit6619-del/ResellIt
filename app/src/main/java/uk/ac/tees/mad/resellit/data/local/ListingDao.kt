package uk.ac.tees.mad.resellit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    @Insert
    suspend fun insertListing(listing: ListingEntity)
    @Query("DELETE FROM my_listings WHERE listingId = :listingId")
    suspend fun deleteListing(listingId: String)
    @Query("SELECT * FROM my_listings")
    fun getAllListings(): Flow<List<ListingEntity>>

    @Query("DELETE FROM my_listings")
    suspend fun deleteAllListings()
}
