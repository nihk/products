package takehomeassignment.app.initializers

import javax.inject.Inject

class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>,
    private val comparator: AppInitializersComparator
) : Initializer {

    override fun initialize() {
        initializers
            .sortedWith(comparator)
            .forEach(Initializer::initialize)
    }
}
