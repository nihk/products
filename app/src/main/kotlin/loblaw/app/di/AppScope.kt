package loblaw.app.di

import javax.inject.Scope

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class AppScope