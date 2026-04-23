package uk.ac.tees.mad.resellit.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.decodeIfNotEmptyOrDefault
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import uk.ac.tees.mad.resellit.data.model.InsertDto
import uk.ac.tees.mad.resellit.data.model.ListingDto
import uk.ac.tees.mad.resellit.util.Constants

class SupabaseService (private val supabaseClient: SupabaseClient){


    suspend fun getInitialListings(): List<ListingDto> {
        return supabaseClient.postgrest["listings"]
            .select {
                order("created_at", Order.DESCENDING)
                limit(20)
            }
            .decodeList<ListingDto>()
    }

    suspend fun getOlderListings(cursor: String): List<ListingDto> {
        return supabaseClient.postgrest["listings"]
            .select {
                filter { lt("created_at", cursor) }
                order("created_at", Order.DESCENDING)
                limit(20)
            }
            .decodeList<ListingDto>()
    }

    suspend fun getNewListings(cursor: String): List<ListingDto> {
        return supabaseClient.postgrest["listings"]
            .select {
                filter { gt("created_at", cursor) }
                order("created_at", Order.DESCENDING)
            }
            .decodeList<ListingDto>()
    }


    suspend fun observeInsertEvents(onInsert: (ListingDto) -> Unit) {

        val channel = supabaseClient.realtime.channel("listings-feed")

        channel.postgresChangeFlow<PostgresAction.Insert>(schema = "public")
        { table = "listings" }
            .collect {item->
                val listing = item.decodeRecord<ListingDto>()
            onInsert(listing)
        }
        channel.subscribe()
    }
    suspend fun createListing(listing: InsertDto) {
        supabaseClient.postgrest["listings"].insert(listing)
    }

    fun getAuthId() = supabaseClient.auth.currentUserOrNull()?.id

    suspend fun uploadImage(image: ByteArray, authId : String,
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

    suspend fun loadOnLogin(userId: String):List<ListingDto>{
        return supabaseClient
            .postgrest["listings"]
            .select {
                filter {
                    eq("user_id" ,userId)
                }
            }
            .decodeList<ListingDto>()

    }
}