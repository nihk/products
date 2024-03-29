package takehomeassignment.remoteproducts.services

import retrofit2.http.GET
import takehomeassignment.remoteproducts.models.Cart

interface ProductsService {
    @GET("nihk/de4ceb2c95633c10196e9f7f067ccfe3/raw/7aac0af7a7c5867ee7958796221df40dfd89f4c8/cart.json")
    suspend fun cart(): Cart
}
