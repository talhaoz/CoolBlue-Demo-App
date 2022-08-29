package com.talhaoz.coolblueapp.fragmentTest

import androidx.test.filters.SmallTest
import com.talhaoz.coolblueapp.ui.fragment.SearchFragment
import com.talhaoz.coolblueapp.ui.fragment.SplashFragment
import com.talhaoz.coolblueapp.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FragmentsTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testSearchFragmentLaunchInHiltContainer(){
        launchFragmentInHiltContainer<SearchFragment> {  }
    }

    @Test
    fun testSplashFragmentLaunchInHiltContainer(){
        launchFragmentInHiltContainer<SplashFragment> {  }
    }
}