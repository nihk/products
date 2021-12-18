package takehomeassignment.productlist.repository

import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.models.ProductListItem

internal fun List<takehomeassignment.remoteproducts.models.Product>.toLocalProducts(): List<Product> {
    return map { remoteProduct ->
        with(remoteProduct) {
            Product(
                id = code,
                imageUrl = image,
                name = name,
                price = price,
                type = type
            )
        }
    }
}

internal fun List<Product>.toProductItems(): List<ProductListItem> {
    return map { product ->
        ProductListItem(
            id = product.id,
            imageUrl = product.imageUrl,
            name = product.name,
            price = product.price
        )
    }
}
