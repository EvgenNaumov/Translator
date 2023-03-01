package geekbrains.ru.translator.koin

import androidx.room.Room
import geekbrains.ru.translator.db.HistoryDataBase
import geekbrains.ru.translator.db.HistoryInteractor
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.model.repository.RepositoryImplementationLocal
import geekbrains.ru.translator.model.repository.RepositoryLocal
import geekbrains.ru.translator.view.history.HistoryViewModel
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.viewmodel.MainActivityViewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(), HistoryDataBase::class.java,
            "HistoryDB"
        ).build()
    }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainActivityViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}
