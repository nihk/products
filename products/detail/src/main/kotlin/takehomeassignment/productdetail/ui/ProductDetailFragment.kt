package takehomeassignment.productdetail.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.savedstate.SavedStateRegistryOwner
import coil.ImageLoader
import coil.load
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import takehomeassignment.productdetail.R
import takehomeassignment.productdetail.databinding.ProductDetailFragmentBinding
import takehomeassignment.productdetail.vm.ProductDetailViewModel

internal class ProductDetailFragment @Inject constructor(
    viewModelFactory: (SavedStateRegistryOwner, id: String) -> ViewModelProvider.Factory,
    private val imageLoader: ImageLoader
) : Fragment(R.layout.product_detail_fragment) {
    private val viewModel by viewModels<ProductDetailViewModel> {
        viewModelFactory(this, id)
    }
    private val id: String get() = arguments?.getString(ARG_ID)!!

    private fun prepareTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            setPathMotion(MaterialArcMotion())
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

        viewModel.states
            .mapNotNull { state -> state.product }
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
        private const val ARG_ID = "id"

        fun bundle(id: String): Bundle {
            return bundleOf(ARG_ID to id)
        }
    }
}
