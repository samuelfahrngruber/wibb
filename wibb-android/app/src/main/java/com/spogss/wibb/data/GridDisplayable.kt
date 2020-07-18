package com.spogss.wibb.data

/**
 * Interface for items that can easily be shown in a Wibb Grid.
 */
interface GridDisplayable {
    /**
     * The image url of the icon.
     */
    val iconUrl: String

    /**
     * The background color behind the icon.
     */
    val iconBgCol: String

    /**
     * The text to be displayed in the Tile.
     */
    val text: String
}
