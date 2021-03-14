package takehomeassignment.productlist.repository

import takehomeassignment.localproducts.models.Product

internal fun List<takehomeassignment.remoteproducts.models.Product>.toLocalProducts(): List<Product> {
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