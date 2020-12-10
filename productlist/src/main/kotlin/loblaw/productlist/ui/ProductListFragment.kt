package loblaw.productlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import loblaw.productlist.R
import loblaw.productlist.databinding.ProductListFragmentBinding
import loblaw.productlist.vm.ProductListViewModel
import javax.inject.Inject

class ProductListFragment @Inject constructor(
    vmFactory: ProductListViewModel.Factory
): Fragment(R.layout.product_list_fragment) {

    private val viewModel by viewModels<ProductListViewModel> { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ProductListFragmentBinding.bind(view)
    }
}