package takehomeassignment.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import takehomeassignment.app.R
import takehomeassignment.app.di.AppGraph
import takehomeassignment.app.di.MainGraph

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainGraph = MainGraph(
            appGraph = (application as AppGraph.Holder).appGraph,
            fragmentManager = supportFragmentManager
        )
        supportFragmentManager.fragmentFactory = mainGraph.fragmentFactory

        super.onCreate(savedInstanceState)

        mainGraph.toProductList.go()
    }
}
