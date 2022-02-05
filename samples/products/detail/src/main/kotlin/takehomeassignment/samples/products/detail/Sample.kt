package takehomeassignment.samples.products.detail

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import coil.imageLoader
import kotlinx.coroutines.launch
import takehomeassignment.localproducts.dao.ProductsDaoProvider
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productdetail.di.ProductDetailGraph
import takehomeassignment.productdetail.ui.ProductDetailDirections
import takehomeassignment.samples.products.detail.databinding.ProductDetailActivityBinding

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val dao = (application as SampleApplication).database.productsDao()
        supportFragmentManager.fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                val graph = ProductDetailGraph(
                    dao = dao,
                    imageLoader = application.imageLoader
                )
                return graph.productDetailFragment.second.invoke()
            }
        }

        super.onCreate(savedInstanceState)

        val binding = ProductDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                dao.nukeThenInsert(
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

                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    val directions = ProductDetailDirections("062600300751")
                    replace(
                        R.id.container,
                        directions.screen,
                        directions.arguments
                    )
                }
            }
        }
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
