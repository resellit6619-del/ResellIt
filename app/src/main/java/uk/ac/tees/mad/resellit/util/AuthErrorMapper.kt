package uk.ac.tees.mad.resellit.util

fun mapAuthError(e: Exception): Exception {
    val message = e.message ?: ""

    return when {

        message.contains("User already registered", true) ->
            IllegalStateException("User already exists")

        message.contains("Invalid login credentials", true) ->
            IllegalStateException("Invalid email or password")

        else ->
            IllegalStateException(message)
    }
}