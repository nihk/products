package takehomeassignment.productlist.ui

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import takehomeassignment.productlist.databinding.ProductItemBinding
import takehomeassignment.productlist.models.ProductListItem

internal class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductListItem, productClick: (ProductListItem) -> Unit, imageLoader: ImageLoader) {
        with(binding) {
            name.text = product.name
            price.text = product.price
            image.load(product.imageUrl, imageLoader)
            image.transitionName = product.id
            image.tag = product.id
            card.setOnClickListener {
                productClick(product)
            }
        }
    }
}
