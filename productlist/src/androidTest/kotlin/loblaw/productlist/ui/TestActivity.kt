package loblaw.productlist.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class TestActivity : AppCompatActivity(), OnProductClicked {

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commitNow()
    }

    override fun onProductClicked(id: String) = Unit
}