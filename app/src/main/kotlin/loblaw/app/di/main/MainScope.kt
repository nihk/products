package loblaw.app.di.main

import javax.inject.Scope

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class MainScope