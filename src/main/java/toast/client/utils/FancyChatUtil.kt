package toast.client.utils

import java.util.*

/**
 * Utility for formatting strings
 */
object FancyChatUtil {
    /**
     * Makes text rainbow using minecraft color codes
     */
    fun rainbowText(text: String): String {
        val colors = arrayOf("&c", "&6", "&e", "&a", "&b", "&9", "&d")
        val result = arrayOf("")
        val index = intArrayOf(-1)
        Arrays.stream(text.split(" ".toRegex()).toTypedArray()).forEach { line: String ->
            Arrays.stream(line.split("".toRegex()).toTypedArray()).forEach { letter: String ->
                if (index[0] + 1 >= colors.size) index[0] -= colors.size
                index[0]++
                result[0] += colors[index[0]] + letter
            }
            result[0] += " "
        }
        return result[0]
    }

    /**
     * Makes text alternate between uppercase and lowercase letters
     */
    fun retardChat(text: String): String {
        val result = StringBuilder()
        var upperCase = true
        for (letter in text.split("".toRegex()).toTypedArray()) {
            result.append(when {
                upperCase -> {
                    upperCase = false
                    letter.toUpperCase()
                }
                !upperCase -> {
                    upperCase = true
                    letter.toLowerCase()
                }
                else -> letter
            })
        }
        return result.toString()
    }

    /**
     * Converts normal letters into Small Capitals
     */
    fun classicFancy(text: String): String {
        return toFancy(text)
    }

    /**
     * Adds a space after every letter
     */
    fun spaces(text: String): String {
        val finalString = StringBuilder()
        val text2 = text.replace(" ".toRegex(), "")
        for (letter in text2.split("".toRegex()).toTypedArray()) finalString.append(letter).append(" ")
        return finalString.toString()
    }

    // https://en.wikipedia.org/wiki/Halfwidth_and_Fullwidth_Forms_(Unicode_block)
    // later? https://en.wikipedia.org/wiki/Mathematical_Alphanumeric_Symbols

    /**
     * Turns a string of fullwidth letters into normal letters
     */
    fun unFancy(s: String): String {
        var noFancyString = ""
        for (c in s) {
            noFancyString += if (c.toInt() in 0xFF01..0xFF5E) c - 0xFEE0 else c
        }
        return noFancyString
    }

    /**
     * Turns a string of normal letters into a string of fullwidth letters
     */
    private fun toFancy(s: String): String {
        var fancyString = ""
        for (c in s) {
            fancyString += if (c.toInt() in 0x21..0x7E) c + 0xFEE0 else c
        }
        return fancyString
    }
}