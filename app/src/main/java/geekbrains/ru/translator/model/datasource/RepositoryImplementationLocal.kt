package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.RepositoryLocal

class RepositoryImplementationLocal(
    private val dataSource:DataSourceLocal<List<DataModel>>
) : RepositoryLocal<List<DataModel>> {
    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}