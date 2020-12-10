package loblaw.localproducts.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import loblaw.localproducts.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class FakeDatabase : RoomDatabase(), ProductsDaoProvider