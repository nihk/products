package takehomeassignment.productlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.databinding.ProductItemBinding

class ProductListAdapter(
    private val onProductClicked: OnProductClicked,
    private val imageLoader: ImageLoader
) : ListAdapter<Product, ProductViewHolder>(ProductDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return LayoutInflater.from(parent.context)
            .let { inflater -> ProductItemBinding.inflate(inflater, parent, false) }
            .let(::ProductViewHolder)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), onProductClicked, imageLoader)
    }
}

private object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
