package loblaw.productlist.ui

import android.widget.ImageView

interface OnProductClicked {
    fun onProductClicked(id: String, image: ImageView)
}