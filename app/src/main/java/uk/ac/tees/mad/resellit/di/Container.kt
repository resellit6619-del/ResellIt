package uk.ac.tees.mad.resellit.di

import android.content.Context
import androidx.room.Room
import uk.ac.tees.mad.resellit.data.local.ListingDatabase
import uk.ac.tees.mad.resellit.data.remote.SupabaseService
import uk.ac.tees.mad.resellit.data.repository.AuthRepositoryImpl
import uk.ac.tees.mad.resellit.data.repository.ListingRepositoryImpl
import uk.ac.tees.mad.resellit.preference.PreferenceManager

class Container (private val context : Context){

    /**
     * it is preference manager which stores values in key value pair format
     */
    val preferenceManager by lazy {
        PreferenceManager(context)
    }

    /**
     * it is database instance which can have multiple table and converters
     */
    val database by lazy {
        Room.databaseBuilder(
            context ,
            ListingDatabase ::class.java ,
            "resellit_db"
        ).build()
    }

    /**
     * it is dao which will be used to communicate with the my_listings table
     */
    val myListingDao by lazy {
        database.myListingDao()
    }

    /**
     * this dao will be used to communicate with the all_listings table
     */
    val listingDao by lazy {
        database.listingDao()
    }



    /**
     * it is an instance of supabase client
     */

    val supabaseClient by lazy{
        createSupabaseClient()
    }

    /**
     * it is an instance of authRepository which is having methods like signIn , signOut , signUp
     */
    val authRepository by lazy{
        AuthRepositoryImpl(supabaseClient , preferenceManager)
    }


    /**
     * it is supabase service instance which is acting as remote data source here for now
     * repository communicate with this supabase service when it needs
     */
    private val supabaseService by lazy {
        SupabaseService(supabaseClient)
    }

    /**
     * it is listing repository instance which is being injected into viewmodel since it has
     * function which communicate with remote and local data source
     */
    val listingRepository by lazy {
        ListingRepositoryImpl(
            supabaseService = supabaseService,
            myListingDao = myListingDao,
            listingDao = listingDao,
            context = context
        )
    }


}


/**
 * this is a dependency container will contain the instances which is required in the project
 */