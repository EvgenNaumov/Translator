package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {


    protected val viewModelCourutinesScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    abstract fun handleError(throwable: Throwable)

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob(){
        viewModelCourutinesScope.coroutineContext.cancelChildren()
    }
}