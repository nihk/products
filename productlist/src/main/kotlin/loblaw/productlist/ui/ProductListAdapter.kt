package loblaw.productlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import loblaw.localproducts.models.Product
import loblaw.productlist.R

class ProductListAdapter(
    private val onProductClicked: OnProductClicked
) : ListAdapter<Product, ProductViewHolder>(ProductDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
            .let(::ProductViewHolder)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), onProductClicked)
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