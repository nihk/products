package loblaw.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import loblaw.localproducts.dao.ProductsDaoProvider
import loblaw.localproducts.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(), ProductsDaoProvider {
    companion object {
        const val name = "loblaw_products.db"
    }
}