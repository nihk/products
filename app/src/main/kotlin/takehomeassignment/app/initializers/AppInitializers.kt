package takehomeassignment.app.initializers

class AppInitializers(
    private val initializers: List<Initializer>
) : Initializer {

    override fun initialize() {
        initializers.forEach(Initializer::initialize)
    }
}
