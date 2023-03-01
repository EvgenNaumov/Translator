package geekbrains.ru.translator.view.history

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import geekbrains.ru.translator.databinding.HistoryActivityRecyclerviewBinding
import geekbrains.ru.translator.db.HistoryInteractor
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.view.base.BaseActivity
import geekbrains.ru.translator.viewmodel.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import geekbrains.ru.translator.view.history.HistoryViewModel as HistoryViewModel

class HistoryActivity:BaseActivity<AppState,HistoryInteractor>() {

    private lateinit var binding:HistoryActivityRecyclerviewBinding
    override lateinit var model: BaseViewModel<AppState>
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = HistoryActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }

    private fun initViewModel() {
        if (binding.historyActivityRecyclerview.adapter!=null){
            throw java.lang.IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        (model as HistoryViewModel).subscribe().observe(this@HistoryActivity, Observer<AppState> {
            renderData(it)
        })
    }


}