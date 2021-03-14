package takehomeassignment.app.navigation

object IdGenerator {
    private var count = 1
    fun next() = count++
}
