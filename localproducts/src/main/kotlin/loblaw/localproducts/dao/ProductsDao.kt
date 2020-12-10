package loblaw.localproducts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import loblaw.localproducts.models.Product

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<Product>): List<Long>

    @Query(
        """
            DELETE
            FROM products
        """
    )
    suspend fun nuke(): Int

    @Query(
        """
            SELECT *
            FROM products
        """
    )
    fun queryAll(): Flow<List<Product>>

    @Query(
        """
            SELECT *
            FROM products
            WHERE id = :id
        """
    )
    suspend fun queryById(id: String): Product?
}