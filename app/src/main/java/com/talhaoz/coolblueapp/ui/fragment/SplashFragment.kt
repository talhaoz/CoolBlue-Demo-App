package com.talhaoz.coolblueapp.ui.fragment

import androidx.lifecycle.lifecycleScope
import com.talhaoz.coolblueapp.databinding.FragmentSplashBinding
import com.talhaoz.coolblueapp.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    override fun onCreateFinished() {
        lifecycleScope.launch {
            delay(1000)
            navigate(SplashFragmentDirections.actionSplashFragmentToSearchFragment())
        }
    }
}