package takehomeassignment.productlist.ui

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.databinding.ProductItemBinding

class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, onProductClicked: OnProductClicked, imageLoader: ImageLoader) {
        with(binding) {
            name.text = product.name
            price.text = product.price
            image.load(product.imageUrl, imageLoader)
            image.transitionName = product.id
            card.setOnClickListener { onProductClicked.onProductClicked(product.id, image) }
        }
    }
}
