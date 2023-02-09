package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.parseSearchResults
import geekbrains.ru.translator.view.main.MainInteractor
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForviewToObserve: LiveData<AppState> = _mutableLiveData


    fun subscribe():LiveData<AppState> = liveDataForviewToObserve

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCourutinesScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO){
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
    }


    override fun onCleared() {
        super.onCleared()
    }
}