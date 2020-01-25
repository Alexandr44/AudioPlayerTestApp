package com.alex44.audioplayertestapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alex44.audioplayertestapp.R
import com.alex44.audioplayertestapp.common.interfaces.IImageLoader
import com.alex44.audioplayertestapp.presenters.HomePresenter
import com.alex44.audioplayertestapp.views.HomeRVItemView
import kotlinx.android.synthetic.main.fragment_home_rv_item.view.*
import javax.inject.Inject
import javax.inject.Named

class HomeRVAdapter(private val presenter : HomePresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @field: [Inject Named("Glide")]
    lateinit var imageLoader : IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_home_rv_item, parent, false)
        )
    }

    override fun getItemCount() = presenter.data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataHolder  = holder as? DataHolder
        dataHolder?.apply {
            elementPosition = position
            presenter.bind(this)
            itemView.setOnClickListener { presenter.clicked(position) }
        }
    }

    inner class DataHolder(private val view : View) : RecyclerView.ViewHolder(view), HomeRVItemView {

        var elementPosition : Int = 0

        override fun setArtistName(artistName: String) {
            view.rv_artist_text.text = artistName
        }

        override fun setTrackName(trackName: String) {
            view.rv_track_text.text = trackName
        }

        override fun setPhoto(photoUrl: String) {
            imageLoader.loadInto(photoUrl, view.rv_item_image, 120)
        }

        override fun getItemPosition(): Int {
            return elementPosition
        }
    }

}