package loblaw.productlist.repository

import loblaw.localproducts.models.Product

internal fun List<loblaw.remoteproducts.models.Product>.toLocalProducts(): List<Product> {
    return map { remoteProduct ->
        with(remoteProduct) {
            Product(
                id = code,
                imageUrl = image,
                name = name,
                price = price,
                type =type
            )
        }
    }
}