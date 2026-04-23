package uk.ac.tees.mad.resellit.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.resellit.data.local.ListingDao
import uk.ac.tees.mad.resellit.data.local.MyListingDao
import uk.ac.tees.mad.resellit.data.model.InsertDto
import uk.ac.tees.mad.resellit.data.remote.SupabaseService
import uk.ac.tees.mad.resellit.domain.model.DomainListing
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository
import uk.ac.tees.mad.resellit.util.toDomain
import uk.ac.tees.mad.resellit.util.toDomainListing
import uk.ac.tees.mad.resellit.util.toListingEntity
import uk.ac.tees.mad.resellit.util.toMyListingEntity
import java.util.UUID

class ListingRepositoryImpl(
    private val supabaseService: SupabaseService,
    private val myListingDao: MyListingDao,
    private val listingDao: ListingDao,
    private val context: Context
) : ListingRepository {

    override suspend fun createListing(
        price: String,
        title: String,
        description: String,
        location: String,
        imageUrls: List<Uri>
    ): Result<Unit> {

        return try {
            val userId = supabaseService.getAuthId()
                ?: throw Exception("User not logged in")
            val listingId = UUID.randomUUID().toString()
            Log.d("Repo", "$listingId $userId")

            val images = uploadImages(
                images = imageUrls,
                userId = userId,
                listingId = listingId
            )
            Log.d("Repo", "$images")
            val insertDto = InsertDto(
                listing_id = listingId,
                title = title,
                description = description,
                price = price,
                location = location,
                image_urls = images,
                user_id = userId,
            )
            supabaseService.createListing(insertDto)
          myListingDao.insertListing(insertDto.toMyListingEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("Repo", e.message.toString())
            Result.failure(e)
        }
    }

   override suspend fun observeListings(): Flow<List<DomainListing>> = withContext(Dispatchers.IO) {
       listingDao.observeListing().map { it ->
           it.map { it.toDomain() }
       }
   }

    override suspend fun refreshFeed() :Result<Unit> = withContext(Dispatchers.IO) {
       return@withContext try {
            val newest = listingDao.getNewestTimeStamp()

            val listings = if (newest == null) {
                supabaseService.getInitialListings()
            } else {
                supabaseService.getNewListings(newest)
            }

            listingDao.insertAll(listings.map { it.toListingEntity() })
            listingDao.trimCache()
           Result.success(Unit)

        } catch (e: Exception) {
            Log.e("Repo", "refreshFeed failed: ${e.message}")
           Result.failure(e)
        }
    }

    override suspend fun loadOlder() = withContext(Dispatchers.IO) {
        try {

            val cursor = listingDao.getOldestTimeStamp() ?: return@withContext
            val listings = supabaseService.getOlderListings(cursor)

            listingDao.insertAll(listings.map { it.toListingEntity() })

        } catch (e: Exception) {
            Log.e("Repo", "loadOlder failed: ${e.message}")
        }
    }



    override suspend fun observeRealtime() {
        try {
            supabaseService.observeInsertEvents { listing ->
                CoroutineScope(Dispatchers.IO).launch {
                    listingDao.insert(listing.toListingEntity())
                }
            }
        } catch (e: Exception) {
            Log.e("Repo", "realtime failed: ${e.message}")
        }
    }

    override suspend fun loadOnLogin(): Result<Unit> {
        return try {
            val userId = supabaseService.getAuthId()?:throw Exception("User not logged in")
            val listing = supabaseService.loadOnLogin(userId)
            myListingDao.insertAll(listing.map {
                it.toMyListingEntity()
            })
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteOnLogout(): Result<Unit> {
        return try{
            listingDao.deleteOnLogout()
            myListingDao.deleteAllListings()
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun fetchByListingId(listingId: String): Result<DomainListing> {
        return try {
            val listing = listingDao.fetchByListingId(listingId)
            Result.success(listing.toDomain())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getUserListings(): Result<Flow<List<DomainListing>>> {
    return try {
        Result.success(myListingDao.getAllListings().map { it -> it.map { it.toDomainListing() } })
    } catch (e: Exception) {
        Result.failure(e)
    }
}

override suspend fun deleteListing(listingId: String): Result<Unit> {
    return try {
        supabaseService.deleteByListingId(listingId)
        myListingDao.deleteListing(listingId)
        listingDao.deleteById(listingId)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.d("Repo", e.message.toString())
        Result.failure(e)
    }

}

override suspend fun deleteAllListing(): Result<Unit> {
    return try {
        val userId = supabaseService.getAuthId()
            ?: throw Exception("User not logged in")
        supabaseService.deleteAllListing(userId)
        myListingDao.deleteAllListings()
        listingDao.deleteByUserId(userId)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.d("Repo", e.message.toString())
        Result.failure(e)
    }
}

private suspend fun uploadImages(
    images: List<Uri>,
    userId: String,
    listingId: String
): List<String> = coroutineScope {
    images.map { uri ->
        async {
            val bytes = uriToByteArray(uri)
            supabaseService
                .uploadImage(
                    image = bytes,
                    authId = userId,
                    listingId = listingId
                )
        }
    }.awaitAll()
}

private fun uriToByteArray(
    uri: Uri,
): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalStateException("Cannot read image")
    return inputStream.readBytes()
}
}