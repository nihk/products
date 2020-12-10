package loblaw.remoteproducts.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val entries: List<Product>
)