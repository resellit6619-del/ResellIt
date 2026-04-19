package uk.ac.tees.mad.resellit.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import uk.ac.tees.mad.resellit.util.Constants

fun createSupabaseClient(): SupabaseClient{
    return createSupabaseClient(
        supabaseUrl = Constants.URL.value,
        supabaseKey = Constants.APIKEY.value
    ){
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)

    }
}

/**
 * this is a supabase module which has a function which will create object of supabase
 */