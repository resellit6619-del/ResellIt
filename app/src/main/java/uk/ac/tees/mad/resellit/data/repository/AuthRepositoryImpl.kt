package uk.ac.tees.mad.resellit.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import uk.ac.tees.mad.resellit.domain.repository.AuthRepository
import uk.ac.tees.mad.resellit.preference.PreferenceManager
import uk.ac.tees.mad.resellit.util.mapAuthError

class AuthRepositoryImpl(private val client: SupabaseClient ,
    private val preferenceManager: PreferenceManager
):
 AuthRepository{

    /**
     * login function to login the existing user
     */

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<Unit> {

        return try {
            client.auth.signInWith(Email){
                this.email = email
                this.password = password
            }

            val user = client.auth.currentUserOrNull()
                ?: throw IllegalStateException("Login failed")
            preferenceManager.setLoggedIn(true)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(mapAuthError(e))
        }
    }

    /**
     * register function to register new user
     */
    override suspend fun registerUser(
        email: String,
        password: String
    ): Result<Unit> {

        return try {
           client.auth.signUpWith(Email){
                this.email = email
                this.password = password
            }

            val user = client.auth.currentUserOrNull()
                ?: throw IllegalStateException("Registration failed")
            preferenceManager.setLoggedIn(true)
            client.postgrest["profiles"].insert(
                buildMap {
                    put("id", user.id)
                    put("email", email)
                }
            )
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(mapAuthError(e))
        }
    }

    /**
     * logout function
     */
    override suspend fun logout(): Result<Unit> {
        return try {
            client.auth.signOut()
            preferenceManager.setLoggedIn(false)
            Result.success(Unit)
    }catch (e : Exception){
        Result.failure(mapAuthError(e))
        }
    }

    override suspend fun fetchEmail(): String {
        return client.auth.currentUserOrNull()?.email ?: "No user profile"
    }
}



/**
 * register function first try to register the user when it succeed then
 * set the profile on the "profiles" table on supabase
 * if any error arises it throws it .
 */