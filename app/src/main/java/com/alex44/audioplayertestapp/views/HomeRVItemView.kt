package com.alex44.audioplayertestapp.views

interface HomeRVItemView {

    fun setArtistName(artistName : String)

    fun setTrackName(trackName : String)

    fun setPhoto(photoUrl : String)

    fun getItemPosition() : Int

}