package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.db.HistoryInteractor
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.utils.parseLocalSearchResults
import kotlinx.coroutines.launch

class HistoryViewModel(private val interactor: HistoryInteractor) :BaseViewModel<AppState>(){

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
    override fun handleError(throwable: Throwable) {
        _mutableLiveData.postValue(AppState.Error(throwable))
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCourutinesScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word,
            isOnline)))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)

        super.onCleared()
    }
}