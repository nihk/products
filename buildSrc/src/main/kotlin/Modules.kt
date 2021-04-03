object Modules {
    const val app = ":app"
    const val core = ":core"

    object Products {
        private const val products = ":products"
        const val detail = "$products:detail"
        const val list = "$products:list"
        const val local = "$products:local"
        const val remote = "$products:remote"
    }

    object Utils {
        private const val utils = ":utils"
        const val async = "$utils:async"
        const val test = "$utils:test"
        const val ui = "$utils:ui"
    }
}
