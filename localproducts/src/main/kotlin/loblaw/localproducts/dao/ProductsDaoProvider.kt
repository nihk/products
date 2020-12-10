package loblaw.localproducts.dao

interface ProductsDaoProvider {
    fun productsDao(): ProductsDao
}