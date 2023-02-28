package geekbrains.ru.translator.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.R
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment
import geekbrains.ru.translator.viewmodel.BaseViewModel
import geekbrains.ru.translator.viewmodel.MainActivityViewModel

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity(){

    abstract var model: MainActivityViewModel

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun renderData(dataModel: T)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }

}
