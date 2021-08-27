package takehomeassignment.samples.products.detail

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import coil.ImageLoader
import coil.imageLoader
import kotlinx.coroutines.launch
import takehomeassignment.localproducts.dao.ProductsDaoProvider
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productdetail.vm.ProductDetailViewModel
import takehomeassignment.samples.products.detail.databinding.ProductDetailActivityBinding

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val productDao = (application as SampleApplication).database.productsDao()
        val vmFactory = ProductDetailViewModel.Factory(productDao)
        supportFragmentManager.fragmentFactory = SampleFragmentFactory(vmFactory, application.imageLoader)

        super.onCreate(savedInstanceState)

        val binding = ProductDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                productDao.nukeThenInsert(
                    listOf(
                        Product(
                            id = "062600300751",
                            imageUrl = "https://assets.beauty.shoppersdrugmart.ca/bb-prod-product-image/062600300751/enfr/01/front/400/white.jpg",
                            name = "RAPID CLEAR® Spot Gel",
                            price = "\$12.99",
                            type = "BeautyFace"
                        )
                    )
                )
                val args = bundleOf(ProductDetailFragment.ARG_ID to "062600300751")
                supportFragmentManager.commit {
                    replace(R.id.container, ProductDetailFragment::class.java, args)
                }
            }
        }
    }
}

class SampleFragmentFactory(
    private val vmFactory: ProductDetailViewModel.Factory,
    private val imageLoader: ImageLoader
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return ProductDetailFragment(vmFactory, imageLoader)
    }
}

class SampleApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, SampleDatabase::class.java, "sample-product-detail.db")
            .build()
    }
}

@Database(
    entities = [Product::class],
    version = 1
)
abstract class SampleDatabase : RoomDatabase(), ProductsDaoProvider
