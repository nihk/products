package loblaw.productlist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import loblaw.localproducts.models.Product
import loblaw.productlist.databinding.ProductItemBinding

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ProductItemBinding.bind(view)

    fun bind(product: Product, onProductClicked: OnProductClicked) {
        with(binding) {
            name.text = product.name
            price.text = product.price
            image.load(product.imageUrl)
            card.setOnClickListener { onProductClicked.onProductClicked(product.id) }
        }
    }
}