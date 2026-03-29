package uk.ac.tees.mad.resellit.preference

import android.content.Context
import androidx.core.content.edit

class PreferenceManager (context : Context){

    companion object{
       const val PREFERENCE = "my_prefs"
        const val LOGIN = "log_in"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(LOGIN ,false)
    }

    fun setLoggedIn(isLoggedIn : Boolean){
        sharedPreferences.edit { putBoolean(LOGIN, isLoggedIn) }
    }


}

/**
 * companion object is a special class that is used yo define a class level values
 * and methods that can be called without having an instance of the class
 * Shared preference is basically a file which store the values in key , value pair format
 * MODE_PRIVATE means that the file is accessible only by the app and not by other apps
 */


/**
 * isLogged in will return whether it is logged in or not and it has default value false
 * set login will set the value to true or false
 */
