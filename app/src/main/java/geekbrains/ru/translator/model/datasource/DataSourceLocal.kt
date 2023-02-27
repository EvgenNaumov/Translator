package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Observable

interface DataSourceLocal<T>:DataSource<T>{
    suspend fun saveToDB(appState: AppState)
}
