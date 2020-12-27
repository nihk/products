package loblaw.uiutils

import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.defaultAnimations(withExit: Boolean = true) {
    anim {
        enter = R.animator.nav_default_enter_anim
        exit = if (withExit) R.animator.nav_default_exit_anim else 0
        popEnter = R.animator.nav_default_pop_enter_anim
        popExit = if (withExit) R.animator.nav_default_pop_exit_anim else 0
    }
}