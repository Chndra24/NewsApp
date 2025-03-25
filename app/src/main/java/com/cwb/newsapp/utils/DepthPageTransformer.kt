package com.cwb.newsapp.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DepthPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            when {
                position < -1 -> { // Page way off-screen to the left
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    alpha = 1f
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    alpha = 1 - position
                    translationX = page.width * -position
                    scaleX = 0.75f + (1 - position) * 0.25f
                    scaleY = 0.75f + (1 - position) * 0.25f
                }
                else -> { // Page way off-screen to the right
                    alpha = 0f
                }
            }
        }
    }
}