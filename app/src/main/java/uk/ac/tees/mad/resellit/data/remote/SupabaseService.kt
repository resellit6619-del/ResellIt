package uk.ac.tees.mad.resellit.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import uk.ac.tees.mad.resellit.data.model.ListingDto
import uk.ac.tees.mad.resellit.util.Constants

class SupabaseService (private val supabaseClient: SupabaseClient){

//    fun getAllListings(): List<ListingDto> {
//
//    }
//
//    fun getUserListings(): List<ListingDto> {
//
//    }

    suspend fun createListing(listing: ListingDto) {
        supabaseClient.postgrest["listings"].insert(listing)
    }

//    fun deleteListing(listingId: String , userId: String) {
//
//    }

    fun getAuthId() = supabaseClient.auth.currentUserOrNull()?.id

    suspend fun uploadImage(image: ByteArray,
                          authId : String,
                          listingId: String): String {
            val fileName = "${System.currentTimeMillis()}.jpg"
            val path = "$authId/$listingId/$fileName"

            supabaseClient
                .storage
                .from(Constants.BUCKET.value)
                .upload(
                    path = path,
                    data = image
                )
            val url = supabaseClient.storage
                .from(Constants.BUCKET.value)
                .publicUrl(path)
      return url
    }

    suspend fun deleteAllListing(userId : String){

        /**
         * fetch all the list associated with this user
         */
        val listings = supabaseClient
            .postgrest["listings"]
            .select {
                filter {
                    eq("user_id", userId)
                }
            }
            .decodeList<ListingDto>()

        /**
         * fetch the path of images stored
         */
        val paths = listings
            .flatMap { it.image_urls }
            .map { url ->
                url.substringAfter("${Constants.BUCKET.value}/")
            }

        /**
         * delete the images at supabase
         */

        if (paths.isNotEmpty()) {
            supabaseClient
                .storage
                .from(Constants.BUCKET.value)
                .delete(paths)
        }

        /**
         * delete the rows at database
         */

        supabaseClient
            .postgrest["listings"]
            .delete {
                filter {
                    eq("user_id" , userId)
                }
            }
    }

    suspend fun deleteByListingId(listingId: String){
        /**
         *first fetch the entire image using auth id , listing id and delete it
         */
        val listing = supabaseClient
            .postgrest["listings"]
            .select{
                filter {
                    eq("listing_id" ,listingId)
                }
            }
            .decodeSingle<ListingDto>()

        val paths = listing.image_urls.map { url ->
            url.substringAfter("listings-bucket/")
        }

        supabaseClient
            .storage
            .from("listings-bucket")
            .delete(paths)

        supabaseClient.postgrest["listings"]
            .delete {
                filter {
                    eq("listing_id" , listingId)
                }
            }
    }
}