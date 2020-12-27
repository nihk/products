package loblaw.app.di

import dagger.Module
import loblaw.app.di.main.MainComponent

@Module(subcomponents = [MainComponent::class])
object SubcomponentsModule