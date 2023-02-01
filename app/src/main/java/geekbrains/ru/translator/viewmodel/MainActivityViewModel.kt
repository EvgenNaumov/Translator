package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.view.main.MainInteractor
import io.reactivex.observers.DisposableObserver

class MainActivityViewModel(
    private val interactor: MainInteractor = MainInteractor(
        remoteRepository = RepositoryImplementation(DataSourceRemote()),
        localRepository = RepositoryImplementation(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word,isOnline)
                .subscribeOn(shedulerProvider.io())
                .observeOn(shedulerProvider.ui())
                .doOnSubscribe{liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object:DisposableObserver<AppState>(){
            override fun onNext(t: AppState) {
                appState = t
                liveDataForViewToObserve.value = t
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}