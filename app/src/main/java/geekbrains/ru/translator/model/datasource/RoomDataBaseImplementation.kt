package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return listOf()
    }
}
