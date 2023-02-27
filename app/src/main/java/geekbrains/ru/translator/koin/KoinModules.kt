package geekbrains.ru.translator.koin

import androidx.room.Room
import geekbrains.ru.translator.NAME_LOCAL
import geekbrains.ru.translator.NAME_REMOTE
import geekbrains.ru.translator.db.HistoryDataBase
import geekbrains.ru.translator.db.HistoryInteractor
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RepositoryImplementationLocal
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.model.repository.RepositoryLocal
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.viewmodel.HistoryViewModel
import geekbrains.ru.translator.viewmodel.MainActivityViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java,
        "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)){
        RepositoryImplementation(RoomDataBaseImplementation(get()))
    }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainActivityViewModel(get()) }

    val historyScreen = module {
        factory { HistoryViewModel(get()) }
        factory { HistoryInteractor(get(), get()) }
    }
}