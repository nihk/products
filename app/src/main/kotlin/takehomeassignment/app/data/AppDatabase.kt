package takehomeassignment.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import takehomeassignment.localproducts.dao.ProductsDaoProvider
import takehomeassignment.localproducts.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(), ProductsDaoProvider {
    companion object {
        const val name = "products.db"
    }
}