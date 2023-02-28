package geekbrains.ru.translator.di

import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.NAME_LOCAL
import geekbrains.ru.translator.NAME_REMOTE
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.view.main.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal:Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}