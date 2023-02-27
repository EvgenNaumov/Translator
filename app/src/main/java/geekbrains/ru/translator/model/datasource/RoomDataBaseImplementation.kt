package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.db.HistoryDao
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.ui.convertDataModelSuccessToEntity
import geekbrains.ru.translator.utils.ui.mapHistoryEntityToSearchResult
import io.reactivex.Observable

class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
