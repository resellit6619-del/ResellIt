package uk.ac.tees.mad.resellit.di

import android.content.Context
import androidx.room.Room
import uk.ac.tees.mad.resellit.data.local.ListingDatabase
import uk.ac.tees.mad.resellit.data.remote.SupabaseService
import uk.ac.tees.mad.resellit.data.repository.AuthRepositoryImpl
import uk.ac.tees.mad.resellit.data.repository.ListingRepositoryImpl
import uk.ac.tees.mad.resellit.preference.PreferenceManager

class Container (private val context : Context){
    val preferenceManager by lazy {
        PreferenceManager(context)
    }


    val database by lazy {
        Room.databaseBuilder(
            context ,
            ListingDatabase ::class.java ,
            "resellit_db"
        ).build()
    }

    val listingDao by lazy {
        database.getListingDao()
    }



    val supabaseClient by lazy{
        createSupabaseClient()
    }
    val authRepository by lazy{
        AuthRepositoryImpl(supabaseClient , preferenceManager)
    }

    private val supabaseService by lazy {
        SupabaseService(supabaseClient)
    }

    val listingRepository by lazy {
        ListingRepositoryImpl(
            supabaseService = supabaseService,
            listingDao = listingDao,
            context = context
        )
    }

}


/**
 * this is a dependency container will contain the instances which is required in the project
 */