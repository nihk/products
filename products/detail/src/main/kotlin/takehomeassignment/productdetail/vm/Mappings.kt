package takehomeassignment.productdetail.vm

import takehomeassignment.localproducts.models.Product
import takehomeassignment.productdetail.models.ProductDetailItem

internal fun Product.toProductDetailItem(): ProductDetailItem {
    return ProductDetailItem(
        id = id,
        imageUrl = imageUrl,
        name = name,
        price = price,
        type = type
    )
}
