package geekbrains.ru.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import geekbrains.ru.translator.viewmodel.MainActivityViewModel

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainViewModel(mainActivityViewModel: MainActivityViewModel):ViewModel
}