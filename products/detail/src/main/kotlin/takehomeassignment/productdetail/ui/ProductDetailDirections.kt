package takehomeassignment.productdetail.ui

import takehomeassignment.core.FragmentDirections

@Suppress("FunctionName")
fun ProductDetailDirections(id: String): FragmentDirections {
    return FragmentDirections(
        screen = ProductDetailFragment::class.java,
        arguments = ProductDetailFragment.bundle(id)
    )
}
