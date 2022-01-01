package takehomeassignment.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import takehomeassignment.app.R
import takehomeassignment.app.databinding.MainActivityBinding
import takehomeassignment.app.di.entryPoint
import takehomeassignment.app.di.main.MainEntryPoint
import takehomeassignment.productlist.ui.ProductListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = entryPoint<MainEntryPoint>()
        supportFragmentManager.fragmentFactory = entryPoint.fragmentFactory
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportFragmentManager.findFragmentById(binding.fragmentContainer.id) == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ProductListFragment>(containerViewId = binding.fragmentContainer.id)
            }
        }
    }
}
