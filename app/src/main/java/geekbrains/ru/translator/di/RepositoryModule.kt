package geekbrains.ru.translator.di

import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.NAME_LOCAL
import geekbrains.ru.translator.NAME_REMOTE
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>
    ): Repository<List<DataModel>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>)
            : Repository<List<DataModel>> = RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal():DataSource<List<DataModel>> = RoomDataBaseImplementation()


}
