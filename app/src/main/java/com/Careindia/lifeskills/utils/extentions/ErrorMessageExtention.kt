import android.content.Context
import android.widget.Toast
fun String.showShortToast(mContext: Context) {
    Toast.makeText(mContext,this,Toast.LENGTH_SHORT).show()
}

fun String.showLongToast(mContext: Context) {
    Toast.makeText(mContext,this,Toast.LENGTH_LONG).show()
}