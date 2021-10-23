package takehomeassignment.productlist.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialSharedAxis
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import takehomeassignment.productlist.R
import takehomeassignment.productlist.databinding.ProductListFragmentBinding
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.ProductClickedEffect
import takehomeassignment.productlist.models.ProductClickedEvent
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.uiutils.MarginItemDecoration
import takehomeassignment.uiutils.isEmpty

class ProductListFragment @Inject constructor(
    vmFactory: ProductListViewModel.Factory.Factory,
    private val onProductClicked: OnProductClicked,
    private val adapterFactory: Provider<ProductListAdapter>
) : Fragment(R.layout.product_list_fragment) {

    private val viewModel by viewModels<ProductListViewModel> { vmFactory.create(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ProductListFragmentBinding.bind(view)
        prepareTransitions()

        val adapter = adapterFactory.get()

        adapter.productClicks()
            .onEach { id -> viewModel.processEvent(ProductClickedEvent(id)) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerView.run {
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.item_outer_gap).roundToInt()))
            this.adapter = adapter
        }

        binding.retry.setOnClickListener {
            viewModel.processEvent(FetchProductsEvent)
        }

        viewModel.viewStates
            .onEach { viewState ->
                viewState.products?.let { products ->
                    adapter.submitList(products)
                    startTransitions()
                }

                binding.progressBar.isVisible = viewState.isLoading
                binding.errorMessage.isVisible = viewState.error != null && adapter.isEmpty
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.viewEffects
            .onEach { viewEffect ->
                when (viewEffect) {
                    is ProductClickedEffect -> {
                        val id = viewEffect.id
                        val image = binding.recyclerView.findViewWithTag<View>(id)
                        onProductClicked.onProductClicked(id, image)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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

    companion object {
        const val ROUTE = "products/list"
    }
}
