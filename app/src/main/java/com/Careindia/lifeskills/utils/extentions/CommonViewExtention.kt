import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.constraintlayout.widget.Group

fun TextView.setNonNullText(data: String?) {
    if (TextUtils.isEmpty(data)) {
        this.text = ""
    } else {
        this.text = data
    }
}

fun TextView.setNonNullText(data: String, defaultValue: String) {
    if (TextUtils.isEmpty(data)) {
        this.text = defaultValue
    } else {
        this.text = data
    }
}

fun TextView.setHtmlText(data: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.setText(
            Html.fromHtml(
                data,
                Html.FROM_HTML_MODE_COMPACT
            )
        );
    } else {
        this.setText(Html.fromHtml(data));
    }
}

fun View.gone() {
    this.visibility = GONE
}


fun View.visible() {
    this.visibility = VISIBLE
}


fun View.inVisible() {
    this.visibility = INVISIBLE
}
fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

