package geekbrains.ru.translator.viewmodel

import geekbrains.ru.translator.db.HistoryInteractor
import geekbrains.ru.translator.model.data.AppState

class HistoryViewModel(private val interactor: HistoryInteractor) :BaseViewModel<AppState>(){
    override fun handleError(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getData(word: String, isOnline: Boolean) {
        TODO("Not yet implemented")
    }
}