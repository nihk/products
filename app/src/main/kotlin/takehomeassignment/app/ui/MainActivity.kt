package takehomeassignment.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import takehomeassignment.app.R
import takehomeassignment.app.di.entryPoint
import takehomeassignment.app.di.main.MainEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryPoint<MainEntryPoint>().toProductList.navigate()
    }
}
