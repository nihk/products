package takehomeassignment.productdetail.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import takehomeassignment.productdetail.R
import takehomeassignment.productdetail.databinding.ProductDetailFragmentBinding
import takehomeassignment.productdetail.vm.ProductDetailViewModel
import javax.inject.Inject

class ProductDetailFragment @Inject constructor(
    vmFactory: ProductDetailViewModel.Factory,
    private val imageLoader: ImageLoader
) : Fragment(R.layout.product_detail_fragment) {

    private val viewModel by viewModels<ProductDetailViewModel> { vmFactory.create(id) }
    private val id: String get() = arguments?.getString(KEY_ID)!!

    private fun prepareTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareTransitions()

        val binding = ProductDetailFragmentBinding.bind(view)
        binding.image.transitionName = id

        viewModel.product()
            .onEach { product ->
                with(binding) {
                    image.load(product.imageUrl, imageLoader) {
                        listener(
                            onSuccess = { _, _ -> startPostponedEnterTransition() },
                            onError = { _, _ -> startPostponedEnterTransition() }
                        )
                    }
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
            return bundleOf(KEY_ID to id)
        }
    }
}