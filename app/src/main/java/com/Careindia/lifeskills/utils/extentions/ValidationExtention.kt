import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidName(): Boolean {
    return this.length >= 2 && Pattern.compile("^[\\p{L} .'-]+$").matcher(this).matches()
}

fun String.isValidPasswordLength(): Boolean {
    return this.length >= 8
}

fun String.isMatchPasswordAndConfirm(confirmPassword: String): Boolean {
    return this == confirmPassword
}


fun String.isValidPhoneNumber(): Boolean {
    return this.length >= 6
}

fun String.isContainText(): Boolean {
    return !Pattern.compile("[0-9]+").matcher(this).matches()



}

