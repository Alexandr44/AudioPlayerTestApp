package com.alex44.audioplayertestapp.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.alex44.audioplayertestapp.App

import com.alex44.audioplayertestapp.R
import com.alex44.audioplayertestapp.common.interfaces.BackButtonListener
import com.alex44.audioplayertestapp.common.interfaces.IImageLoader
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.presenters.DetailPresenter
import com.alex44.audioplayertestapp.views.DetailView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject
import javax.inject.Named

class DetailFragment : MvpAppCompatFragment(), DetailView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @field: [Inject Named("Glide")]
    lateinit var imageLoader : IImageLoader<ImageView>

    companion object {
        fun newInstance(dto : DataDTO) : DetailFragment {
            val arguments = Bundle()
            arguments.putSerializable("data", dto)
            val fragment = DetailFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.instance.appComponent.inject(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @ProvidePresenter
    fun createPresenter() : DetailPresenter {
        val dto = arguments?.getSerializable("data") as? DataDTO
        val presenter = DetailPresenter(dto)
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun initButtons() {
        play_button.setOnClickListener {
            presenter.playClicked()
            pause_button.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        pause_button.setOnClickListener {
            presenter.pauseClicked()
            play_button.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        stop_button.setOnClickListener {
            presenter.stopClicked()
            play_button.visibility = View.VISIBLE
            pause_button.visibility = View.GONE
        }
    }

    override fun setArtistName(artistName: String) {
        detail_artist_name.text = artistName
    }

    override fun setTrackName(trackName: String) {
        detail_track_name.text = trackName
    }

    override fun setPhoto(photoUrl: String) {
        imageLoader.loadInto(photoUrl, detail_photo, 20)
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun backClick(): Boolean {
        return presenter.backClicked()
    }

}
