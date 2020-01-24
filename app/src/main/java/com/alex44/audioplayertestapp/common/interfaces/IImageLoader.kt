package com.alex44.audioplayertestapp.common.interfaces

interface IImageLoader<T> {

    fun loadInto(url : String, container : T, corners : Int)

}