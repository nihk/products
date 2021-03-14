package takehomeassignment.productlist.ui

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.repository.toLocalProducts
import takehomeassignment.remoteproducts.models.Cart
import java.io.BufferedReader
import java.io.InputStreamReader

val products by lazy {
    ProductsData.get("cart.json")
}

private object ProductsData {
    private val moshi = Moshi.Builder().build()
    val cartAdapter: JsonAdapter<Cart> = moshi.adapter(Cart::class.java)

    fun get(relativeFilename: String): List<Product> {
        return cartAdapter.fromJson(relativeFilename.fileToJson())?.entries?.toLocalProducts()!!
    }

    private fun String.fileToJson(): String {
        val inputStream = ProductsData::class.java.classLoader?.getResourceAsStream(this)!!
        return InputStreamReader(inputStream).buffered().use(BufferedReader::readText)
    }
}