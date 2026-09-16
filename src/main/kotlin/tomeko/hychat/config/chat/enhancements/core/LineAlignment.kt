package tomeko.hychat.config.chat.enhancements.core

enum class LineAlignment {
    TOP, CENTER, BOTTOM;

    fun getVerticalOffset(lineHeight: Int): Int = when (this) {
        TOP -> (-lineHeight - 1) / 2
        CENTER -> 0
        BOTTOM -> lineHeight / 2
    }
}
