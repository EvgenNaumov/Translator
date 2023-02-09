package geekbrains.ru.translator.application

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import geekbrains.ru.translator.koin.application
import geekbrains.ru.translator.koin.mainScreen
import org.koin.core.context.startKoin
import javax.inject.Inject

class TranslatorApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(listOf(application, mainScreen))
        }
    }
}