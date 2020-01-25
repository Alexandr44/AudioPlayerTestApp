package com.alex44.audioplayertestapp.common.navigation

import androidx.fragment.app.Fragment
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.ui.fragments.DetailFragment
import com.alex44.audioplayertestapp.ui.fragments.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = HomeFragment.newInstance()
    }

    class DetailScreen(val dto : DataDTO) : SupportAppScreen() {
        override fun getFragment(): Fragment = DetailFragment.newInstance(dto)
    }

}