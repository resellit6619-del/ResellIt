package uk.ac.tees.mad.resellit.domain.repository

interface AuthRepository {
    suspend fun loginUser(email : String , password : String) :Result<Unit>
   suspend fun registerUser(email : String , password : String) :Result<Unit>
   suspend fun logout() : Result<Unit>
}


/**
 * this is an interface AuthRepository which is basically a contract for a class
 * that implements this interface must have the following functions.
 * It creates abstraction
 */
