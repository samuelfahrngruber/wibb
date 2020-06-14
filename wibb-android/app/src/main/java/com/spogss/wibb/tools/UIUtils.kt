package com.spogss.wibb.tools

import android.content.Context
import android.graphics.Color
import com.spogss.wibb.R
import kotlinx.android.synthetic.main.offer_card_shrinked.*

object UIUtils {
    fun getForegroundColorFor(col: Int, context: Context): Int {
        return if(Color.luminance(col) < 0.5) context.getColor(R.color.white)
            else context.getColor(android.R.color.tab_indicator_text)
    }
}