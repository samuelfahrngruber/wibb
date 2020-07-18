package com.spogss.wibb.tools

import android.content.Context
import android.graphics.Color
import com.spogss.wibb.R

/**
 * Contains convenient methods for recurring UI operations.
 */
object UIUtils {

    /**
     * Gets a foreground color for a background color, that is visible nicely.
     * If the luminance of the given background color is below a certain threshold, this will
     * return white, otherwise this will return the default text color.
     * @param col The background color
     * @param context android app context
     * @return The color that should be used for text on this background color.
     */
    fun getForegroundColorFor(col: Int, context: Context): Int {
        return if(Color.luminance(col) < 0.5) context.getColor(R.color.white)
            else context.getColor(android.R.color.tab_indicator_text)
    }
}