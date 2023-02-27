package geekbrains.ru.translator.view.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import geekbrains.ru.translator.R
import geekbrains.ru.translator.databinding.ActivityMainBinding
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.view.base.BaseActivity
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import geekbrains.ru.translator.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var binding: ActivityMainBinding
    override lateinit var model: MainActivityViewModel


    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    private var adapter: MainAdapter = MainAdapter(onListItemClickListener)

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initView()
    }

    private fun initView() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    private fun initViewModel(){
        if (binding.mainActivityRecyclerview.adapter!=null){
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val viewModel: MainActivityViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, { renderData(it) })

    }
    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {

                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
//                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                    showAlertDialog(
                        getString(R.string.dialog_title_device_is_offline),getString(R.string.dialog_message_device_is_offline)
                    )
                    showViewError()
                } else {
                    showViewSuccess()

                    adapter!!.setData(dataModel)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
                showAlertDialog(getString(R.string.error_stub), appState.error.message)

            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }


    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
