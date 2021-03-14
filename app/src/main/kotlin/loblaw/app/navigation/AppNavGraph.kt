package loblaw.app.navigation

object AppNavGraph {
    val id = IdGenerator.next()

    object Destination {
        val productList = IdGenerator.next()
        val productDetail = IdGenerator.next()
    }
}