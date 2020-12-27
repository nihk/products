package loblaw.productlist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import loblaw.localproducts.models.Product
import loblaw.productlist.databinding.ProductItemBinding

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ProductItemBinding.bind(view)

    fun bind(product: Product, onProductClicked: OnProductClicked, imageLoader: ImageLoader) {
        with(binding) {
            name.text = product.name
            price.text = product.price
            image.load(product.imageUrl, imageLoader)
            card.setOnClickListener { onProductClicked.onProductClicked(product.id) }
        }
    }
}