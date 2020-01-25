package com.alex44.audioplayertestapp.common.navigation

import androidx.fragment.app.Fragment
import com.alex44.audioplayertestapp.ui.activities.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = HomeFragment.newInstance();
    }

}