package geekbrains.ru.translator.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.presenter.Presenter
import geekbrains.ru.translator.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity(), View {

    abstract val model:BaseViewModel<T>


    abstract override fun renderData(appState: AppState)

}
