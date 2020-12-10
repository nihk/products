package loblaw.remoteproducts.services

import loblaw.remoteproducts.models.Product
import retrofit2.http.GET

interface ProductsService {

    @GET("r2vq/2ac197145db3f6cdf1a353feb744cf8e/raw/b1e722f608b00ddde138a0eef2261c6ffc8b08d7/cart.json")
    suspend fun products(): List<Product>
}