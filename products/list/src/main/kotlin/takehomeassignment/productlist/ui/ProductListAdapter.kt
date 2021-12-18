package takehomeassignment.productlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import takehomeassignment.productlist.databinding.ProductItemBinding
import takehomeassignment.productlist.models.ProductListItem

class ProductListAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<ProductListItem, ProductViewHolder>(ProductDiffCallback) {
    private val productClick: (ProductListItem) -> Unit = { product -> productClicks.tryEmit(product) }
    private val productClicks = MutableSharedFlow<ProductListItem>(extraBufferCapacity = 1)
    fun productClicks(): Flow<ProductListItem> = productClicks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return LayoutInflater.from(parent.context)
            .let { inflater -> ProductItemBinding.inflate(inflater, parent, false) }
            .let(::ProductViewHolder)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), productClick, imageLoader)
    }
}

private object ProductDiffCallback : DiffUtil.ItemCallback<ProductListItem>() {
    override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem == newItem
    }
}
