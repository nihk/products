package loblaw.testutils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.memory.MemoryCache
import coil.request.*

// Copied from: https://coil-kt.github.io/coil/image_loaders/#testing
class FakeImageLoader : ImageLoader {
    private val drawable = ColorDrawable(Color.GREEN)

    private val disposable = object : Disposable {
        override val isDisposed get() = true
        override fun dispose() = Unit
        override suspend fun await() = Unit
    }

    override val defaults = DefaultRequestOptions()

    override val memoryCache get() = error("Unused")

    override val bitmapPool = BitmapPool(0)

    override fun enqueue(request: ImageRequest): Disposable {
        request.target?.onStart(drawable)
        request.target?.onSuccess(drawable)
        return disposable
    }

    override suspend fun execute(request: ImageRequest): ImageResult {
        return SuccessResult(
            drawable = drawable,
            request = request,
            metadata = ImageResult.Metadata(
                memoryCacheKey = MemoryCache.Key(""),
                isSampled = false,
                dataSource = DataSource.MEMORY_CACHE,
                isPlaceholderMemoryCacheKeyPresent = false
            )
        )
    }

    override fun shutdown() = Unit
}