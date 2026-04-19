package uk.ac.tees.mad.resellit.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.resellit.data.local.ListingDao
import uk.ac.tees.mad.resellit.data.model.ListingDto
import uk.ac.tees.mad.resellit.data.remote.SupabaseService
import uk.ac.tees.mad.resellit.domain.model.DomainListing
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository
import uk.ac.tees.mad.resellit.util.toDomainListing
import uk.ac.tees.mad.resellit.util.toListingEntity
import java.util.UUID

class  ListingRepositoryImpl(
    private val supabaseService: SupabaseService,
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
            Log.d("Repo" , "$listingId $userId")

            val images = uploadImages(
                images = imageUrls,
                userId = userId,
                listingId = listingId
            )
            Log.d("Repo" , "$images")
            val listingDto = ListingDto(
                listing_id = listingId,
                title = title,
                description = description,
                price = price,
                location = location,
                image_urls = images,
                user_id = userId
            )
            supabaseService.createListing(listingDto)
            listingDao.insertListing(listingDto.toListingEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("Repo", e.message.toString())
            Result.failure(e)
        }
    }

    override suspend fun getAllListings() {
        TODO("Not yet implemented")
    }

    override suspend fun getUserListings(): Result<Flow<List<DomainListing>>> {
        return try {
            Result.success(listingDao.getAllListings().map { it -> it.map { it.toDomainListing() } })
        }catch (e :Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteListing(listingId: String): Result<Unit> {
        return try{
            supabaseService.deleteByListingId(listingId)
            listingDao.deleteListing(listingId)
            Result.success(Unit)
        }catch (e : Exception){
            Log.d("Repo" , e.message.toString())
            Result.failure(e)
        }

    }

    override suspend fun deleteAllListing(): Result<Unit> {

        return try{
            val userId = supabaseService.getAuthId()
                ?: throw Exception("User not logged in")
            supabaseService.deleteAllListing(userId)
            listingDao.deleteAllListings()
            Result.success(Unit)
        }catch (e : Exception){
            Log.d("Repo" , e.message.toString())
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