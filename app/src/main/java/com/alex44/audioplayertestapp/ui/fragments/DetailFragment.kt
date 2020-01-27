package com.alex44.audioplayertestapp.ui.fragments


import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import com.alex44.audioplayertestapp.App
import com.alex44.audioplayertestapp.R
import com.alex44.audioplayertestapp.common.interfaces.BackButtonListener
import com.alex44.audioplayertestapp.common.interfaces.IImageLoader
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.model.enums.PlayerState
import com.alex44.audioplayertestapp.presenters.DetailPresenter
import com.alex44.audioplayertestapp.views.DetailView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class DetailFragment : MvpAppCompatFragment(), DetailView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @field: [Inject Named("Glide")]
    lateinit var imageLoader : IImageLoader<ImageView>

    var mediaPlayer : MediaPlayer? = null

    private var mediaPlayerDisposable : Disposable? = null

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
        }
        pause_button.setOnClickListener {
            presenter.pauseClicked()
        }
        stop_button.setOnClickListener {
            presenter.stopClicked()
        }
    }

    /**
     * Инициализация медиа плеера
     * @param previewUrl - url аудио
     */
    override fun initPlayer(previewUrl: String?) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, Uri.parse(previewUrl))
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener {
                seek_bar.max = mediaPlayer?.duration?:0
                track_length.text = fromMSecsToStr(mediaPlayer?.duration?:0)
                presenter.playerPrepared()
            }
            prepareAsync()
        }
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    presenter.positionChangedByUser(progress)   //Обработка нажатий на прогресс бар
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })
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

    override fun onDestroy() {
        super.onDestroy()
        disposeProgressUpdater()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun play() {
        pause_button.visibility = View.VISIBLE
        play_button.visibility = View.GONE
        mediaPlayer?.start()
    }

    override fun pause() {
        play_button.visibility = View.VISIBLE
        pause_button.visibility = View.GONE
        mediaPlayer?.pause()
    }

    override fun stop() {
        play_button.visibility = View.VISIBLE
        pause_button.visibility = View.GONE
        mediaPlayer?.pause()
        mediaPlayer?.seekTo(0)
    }

    /**
     * Обновление положения прогресс бара
     * @param position - новая позиция
     */
    override fun updateBarPosition(position : Int) {
        seek_bar.progress = position
    }

    /**
     * Обновление текущего времени
     * @param position - новая позиция
     */
    override fun updateSecPosition(position: Int) {
        track_current_pos.text = fromMSecsToStr(position)
    }

    /**
     * Обновление положения медиа плеера
     * @param position - новая позиция
     */
    override fun updatePlayerPosition(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    /**
     * Задание начального положения и состояния (в том числе после смены ориентации)
     * @param position - новая позиция
     * @param playerState - состояние плеера
     */
    override fun setStartPosition(progress: Int, playerState : PlayerState) {
        updateBarPosition(progress)
        updatePlayerPosition(progress)
        when (playerState) {
            PlayerState.PLAY -> play()
            PlayerState.PAUSE -> {
                play()  //без этого MediaPlayer выдает ошибку -38 после переворота
                pause()
            }
            PlayerState.STOP -> {
                play()  //без этого MediaPlayer выдает ошибку -38 после переворота
                stop()
            }
            PlayerState.INIT -> {}
        }
        startProgressUpdater()
    }

    private fun fromMSecsToStr(msecs : Int) : String {
        val seconds = (msecs / 1000) % 60
        val minutes = (msecs / (1000 * 60) % 60)
        val hours = (msecs / (1000 * 60 * 60) % 24)
        return if (hours > 0)
            String.format("%02d", hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d", seconds)
        else
            String.format("%02d", minutes)+":"+String.format("%02d", seconds)
    }

    /**
     * Отслеживание изменений позиции проигрывания в медиа плеере
     */
    private fun startProgressUpdater() {
        mediaPlayerDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mediaPlayer?.let {
                    presenter.positionChanged(it.currentPosition)
                }
            }
    }

    private fun disposeProgressUpdater() {
        mediaPlayerDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}
