package takehomeassignment.app.initializers

/**
 * Sometimes [Initializer]s are dependent on other [Initializer]s. This class helps sort them into
 * the appropriate order for initialization, based on the passed-in [priorities].
 */
class AppInitializersComparator(
    private val priorities: List<Class<out Initializer>>
) : Comparator<Initializer> {
    override fun compare(first: Initializer, second: Initializer): Int {
        val firstIndex = priorities.indexOf(first::class.java).handleNotFound()
        val secondIndex = priorities.indexOf(second::class.java).handleNotFound()
        return firstIndex.compareTo(secondIndex)
    }

    private fun Int.handleNotFound(): Int {
        return if (this == -1) {
            Int.MAX_VALUE
        } else {
            this
        }
    }
}
