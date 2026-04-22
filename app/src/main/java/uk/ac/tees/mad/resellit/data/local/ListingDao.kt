package uk.ac.tees.mad.resellit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ListingDao {

    @Query(
        "select * from all_listings order by createdAt Desc"
    )
    fun observeListing(): Flow<List<ListingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listings: List<ListingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listing: ListingEntity)

    @Query(
        "SELECT createdAt FROM all_listings ORDER BY createdAt DESC LIMIT 1"
    )
    fun getNewestTimeStamp(): String?

    @Query(
        "SELECT createdAt FROM all_listings ORDER BY createdAt ASC LIMIT 1"
    )
    fun getOldestTimeStamp(): String?

    @Query("DELETE FROM all_listings WHERE listingId NOT IN ( SELECT listingId FROM all_listings ORDER BY createdAt DESC LIMIT 200)")
    suspend fun trimCache()

    @Query("DELETE FROM ALL_LISTINGS")
    suspend fun deleteOnLogout()

    @Query("DELETE FROM all_listings WHERE listingId = :listingId")
    suspend fun deleteById(listingId: String)

    @Query("DELETE  FROM all_listings WHERE userId = :userId")
    suspend fun deleteByUserId(userId : String)

    @Query("SELECT * FROM all_listings WHERE listingId = :listingId LIMIT 1")
    suspend fun fetchByListingId(listingId : String) : ListingEntity
}

/**
 * this dao is responsible for only fetching data from room to show on the home screen
 * not on the listing screen
 */
