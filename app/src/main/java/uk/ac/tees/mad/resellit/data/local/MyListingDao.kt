package uk.ac.tees.mad.resellit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: MyListingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list : List<MyListingEntity>)

    @Query("DELETE FROM my_listings WHERE listingId = :listingId")
    suspend fun deleteListing(listingId: String)

    @Query("SELECT * FROM my_listings")
    fun getAllListings(): Flow<List<MyListingEntity>>

    @Query("DELETE FROM my_listings")
    suspend fun deleteAllListings()

}
