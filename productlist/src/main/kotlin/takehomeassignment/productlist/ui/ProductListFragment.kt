package takehomeassignment.productlist.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import com.google.android.material.transition.MaterialSharedAxis
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import takehomeassignment.productlist.R
import takehomeassignment.productlist.databinding.ProductListFragmentBinding
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.uiutils.MarginItemDecoration
import takehomeassignment.uiutils.gone
import takehomeassignment.uiutils.isEmpty
import takehomeassignment.uiutils.visible

class ProductListFragment @Inject constructor(
    vmFactory: ProductListViewModel.Factory,
    private val onProductClicked: OnProductClicked,
    private val imageLoader: ImageLoader
) : Fragment(R.layout.product_list_fragment) {

    private val viewModel by viewModels<ProductListViewModel> { vmFactory.create(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ProductListFragmentBinding.bind(view)
        prepareTransitions()

        val adapter = ProductListAdapter(onProductClicked, imageLoader)

        binding.recyclerView.run {
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.item_outer_gap).roundToInt()))
            this.adapter = adapter
        }

        observeViewModel(binding, adapter)
    }

    private fun prepareTransitions() {
        postponeEnterTransition()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    private fun startTransitions() {
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun observeViewModel(binding: ProductListFragmentBinding, adapter: ProductListAdapter) {
        viewModel.loading
            .onEach { isLoading ->
                if (isLoading) {
                    binding.progressBar.visible()
                } else {
                    binding.progressBar.gone()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.products
            .onEach { products ->
                adapter.submitList(products)
                startTransitions()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.error
            .onEach { throwable ->
                if (throwable != null && adapter.isEmpty) {
                    binding.errorMessage.visible()
                } else {
                    binding.errorMessage.gone()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
