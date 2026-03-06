package uk.ac.tees.mad.resellit.util

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS
        .matcher(email)
        .matches()
}