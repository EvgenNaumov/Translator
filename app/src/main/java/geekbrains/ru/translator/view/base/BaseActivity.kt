package geekbrains.ru.translator.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.R
import geekbrains.ru.translator.databinding.ErrorLayoutBinding
import geekbrains.ru.translator.databinding.LoadingLayoutBinding
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment
import geekbrains.ru.translator.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity(){


    private lateinit var binding: LoadingLayoutBinding
    private lateinit var bindingErrorLayout: ErrorLayoutBinding
    abstract var model: BaseViewModel<T>
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

    protected fun renderData(appState: T){
        when(appState){
            is AppState.Success->{
                showViewWorking()
                showViewSuccess()
                appState.data?.let{
                    if (it.isEmpty()){
                        showAlertDialog(
                            getString(R.string.dialog_title_sorry),
                        getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading->{
                showViewLoading()
                if (appState.progress!=null){
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else{
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error->{
                showViewWorking()
                showViewError()
                showErrorScreen(appState.error.message)
                showAlertDialog(getString(R.string.error_stub),
                    appState.error.message)
            }
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

    private fun showViewWorking() {

        binding.loadingLayout.visibility = View.GONE
        bindingErrorLayout.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
        bindingErrorLayout.errorLinearLayout.visibility = View.GONE
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        bindingErrorLayout.errorTextview.text = error ?: getString(R.string.undefined_error)
        bindingErrorLayout.reloadButton.setOnClickListener {
            model.getData("hi", false)
        }
    }

    private fun showViewSuccess() {
        binding.loadingLayout.visibility = View.GONE
        bindingErrorLayout.errorLinearLayout.visibility = View.GONE
    }


    private fun showViewError() {
        binding.loadingLayout.visibility = View.GONE
        bindingErrorLayout.errorLinearLayout.visibility = View.VISIBLE
    }

    abstract fun setDataToAdapter(data: List<DataModel>)


    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }


}
