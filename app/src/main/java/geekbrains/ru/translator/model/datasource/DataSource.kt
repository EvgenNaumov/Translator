package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.AppState
import io.reactivex.Observable

interface DataSource<T> {

    suspend fun getData(word: String): T
}
