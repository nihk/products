package takehomeassignment.localproducts.dao

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import takehomeassignment.localproducts.models.Product

class ProductsDaoTest {

    @get:Rule
    val database = InMemoryDatabaseRule(FakeDatabase::class.java)
    val dao get() = database.database().productsDao()

    @Test
    fun queryByIdGetsCorrectProduct() = runBlocking {
        val products = createProducts("1", "2")
        dao.insert(products)

        val product = dao.queryById("2")

        assertNotNull(product)
        assertEquals(createProducts("2").first(), product)
    }

    @Test
    fun nukeThenInsertClearsPreviousRecords() = runBlocking {
        val products = createProducts("1", "2")
        dao.insert(products)
        val newProducts = createProducts("3", "4")

        dao.nukeThenInsert(newProducts)
        val product = dao.queryById("1")

        assertNull(product)
    }

    private fun createProducts(vararg ids: String): List<Product> {
        return ids.map { id ->
            Product(
                id = id,
                imageUrl = "",
                type = "",
                price = "",
                name = ""
            )
        }
    }
}
