package com.alex44.audioplayertestapp.ui.activities


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex44.audioplayertestapp.App
import com.alex44.audioplayertestapp.R
import com.alex44.audioplayertestapp.presenters.HomePresenter
import com.alex44.audioplayertestapp.ui.adapters.HomeRVAdapter
import com.alex44.audioplayertestapp.views.HomeView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : MvpAppCompatFragment(), HomeView {

    @InjectPresenter
    lateinit var presenter: HomePresenter

    var adapter : HomeRVAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @ProvidePresenter
    fun createPresenter() : HomePresenter {
        val presenter = HomePresenter(AndroidSchedulers.mainThread());
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun initSearch() {
        home_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length > 5) {
                        presenter.searchTextEntered(newText)
                    }
                }
                return true
            }
        })
    }

    override fun initRV() {
        adapter = HomeRVAdapter(presenter)
        App.instance.appComponent.inject(adapter as HomeRVAdapter)
        data_rv.layoutManager = LinearLayoutManager(context)
        data_rv.adapter = adapter
    }

    override fun updateRV() {
        adapter?.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.rvPosition = (data_rv?.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()?:0
    }

    override fun onResume() {
        super.onResume()
        (data_rv?.layoutManager as? LinearLayoutManager)?.scrollToPosition(presenter.rvPosition)
    }

}
