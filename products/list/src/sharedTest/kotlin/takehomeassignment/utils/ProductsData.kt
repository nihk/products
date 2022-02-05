package takehomeassignment.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.InputStreamReader
import takehomeassignment.productlist.models.ProductListItem
import takehomeassignment.productlist.repository.toLocalProducts
import takehomeassignment.productlist.repository.toProductItems
import takehomeassignment.remoteproducts.models.Cart

internal val products by lazy {
    ProductsData.get("cart.json")
}

private object ProductsData {
    private val moshi = Moshi.Builder().build()
    val cartAdapter: JsonAdapter<Cart> = moshi.adapter(Cart::class.java)

    fun get(relativeFilename: String): List<ProductListItem> {
        return cartAdapter.fromJson(relativeFilename.fileToJson())
            ?.entries
            ?.toLocalProducts()
            ?.toProductItems()!!
    }

    private fun String.fileToJson(): String {
        val inputStream = ProductsData::class.java.classLoader?.getResourceAsStream(this)!!
        return InputStreamReader(inputStream).buffered().use(BufferedReader::readText)
    }
}
