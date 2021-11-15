package takehomeassignment.productlist.ui

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import kotlinx.coroutines.flow.MutableSharedFlow
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.databinding.ProductItemBinding

class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product, productClicks: MutableSharedFlow<Product>, imageLoader: ImageLoader) {
        with(binding) {
            name.text = product.name
            price.text = product.price
            image.load(product.imageUrl, imageLoader)
            image.transitionName = product.id
            image.tag = product.id
            card.setOnClickListener {
                productClicks.tryEmit(product)
            }
        }
    }
}
