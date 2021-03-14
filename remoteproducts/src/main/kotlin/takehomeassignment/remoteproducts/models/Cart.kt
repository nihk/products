package takehomeassignment.remoteproducts.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cart(
    val entries: List<Product>
)