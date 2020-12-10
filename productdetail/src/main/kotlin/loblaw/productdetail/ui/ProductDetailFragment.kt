package loblaw.productdetail.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import loblaw.productdetail.R
import loblaw.productdetail.databinding.ProductDetailFragmentBinding
import loblaw.productdetail.vm.ProductDetailViewModel
import javax.inject.Inject

class ProductDetailFragment @Inject constructor(
    vmFactory: ProductDetailViewModel.Factory
) : Fragment(R.layout.product_detail_fragment) {

    private val viewModel by viewModels<ProductDetailViewModel> {
        vmFactory.create(arguments?.getString(KEY_ID)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ProductDetailFragmentBinding.bind(view)
        viewModel.product()
            .onEach { product ->
                with(binding) {
                    image.load(product.imageUrl)
                    name.text = product.name
                    price.text = product.price
                    type.text = getString(R.string.product_type_formatter, product.type)
                    code.text = getString(R.string.product_code_formatter, product.id)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val KEY_ID = "id"

        fun bundle(id: String): Bundle {
            return bundleOf(
                KEY_ID to id
            )
        }
    }
}