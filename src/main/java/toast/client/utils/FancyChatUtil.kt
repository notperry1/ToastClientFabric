package toast.client.utils

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

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
        val letters = classicLetters
        val finalString = StringBuilder()
        for (letter in text.split("".toRegex()).toTypedArray()) finalString.append(if (letter == " ") " " else letters[letter]
                ?: letter)
        return finalString.toString()
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

    private val classicLetters: Map<String, String>
        get() = // TODO: find a easier way to do this
            Stream.of(
                    arrayOf("0", "０"),
                    arrayOf("1", "１"),
                    arrayOf("2", "２"),
                    arrayOf("3", "３"),
                    arrayOf("4", "４"),
                    arrayOf("5", "５"),
                    arrayOf("6", "６"),
                    arrayOf("7", "７"),
                    arrayOf("8", "８"),
                    arrayOf("9", "９"),
                    arrayOf("A", "Ａ"),
                    arrayOf("B", "Ｂ"),
                    arrayOf("C", "Ｃ"),
                    arrayOf("D", "Ｄ"),
                    arrayOf("E", "Ｅ"),
                    arrayOf("F", "Ｆ"),
                    arrayOf("G", "Ｇ"),
                    arrayOf("H", "Ｈ"),
                    arrayOf("I", "Ｉ"),
                    arrayOf("J", "Ｊ"),
                    arrayOf("K", "Ｋ"),
                    arrayOf("L", "Ｌ"),
                    arrayOf("M", "Ｍ"),
                    arrayOf("N", "Ｎ"),
                    arrayOf("O", "Ｏ"),
                    arrayOf("P", "Ｐ"),
                    arrayOf("Q", "Ｑ"),
                    arrayOf("R", "Ｒ"),
                    arrayOf("S", "Ｓ"),
                    arrayOf("T", "Ｔ"),
                    arrayOf("U", "Ｕ"),
                    arrayOf("V", "Ｖ"),
                    arrayOf("W", "Ｗ"),
                    arrayOf("X", "Ｘ"),
                    arrayOf("Y", "Ｙ"),
                    arrayOf("Z", "Ｚ"),
                    arrayOf("a", "ａ"),
                    arrayOf("b", "ｂ"),
                    arrayOf("c", "ｃ"),
                    arrayOf("d", "ｄ"),
                    arrayOf("e", "ｅ"),
                    arrayOf("f", "ｆ"),
                    arrayOf("g", "ｇ"),
                    arrayOf("h", "ｈ"),
                    arrayOf("i", "ｉ"),
                    arrayOf("j", "ｊ"),
                    arrayOf("k", "ｋ"),
                    arrayOf("l", "ｌ"),
                    arrayOf("m", "ｍ"),
                    arrayOf("n", "ｎ"),
                    arrayOf("o", "ｏ"),
                    arrayOf("p", "ｐ"),
                    arrayOf("q", "ｑ"),
                    arrayOf("r", "ｒ"),
                    arrayOf("s", "ｓ"),
                    arrayOf("t", "ｔ"),
                    arrayOf("u", "ｕ"),
                    arrayOf("v", "ｖ"),
                    arrayOf("w", "ｗ"),
                    arrayOf("x", "ｘ"),
                    arrayOf("y", "ｙ"),
                    arrayOf("z", "ｚ"),
                    arrayOf("!", "！"),
                    arrayOf("\"", "＂"),
                    arrayOf("#", "＃"),
                    arrayOf("$", "＄"),
                    arrayOf("%", "％"),
                    arrayOf("&", "＆"),
                    arrayOf("'", "＇"),
                    arrayOf("(", "（"),
                    arrayOf(")", "）"),
                    arrayOf("*", "＊"),
                    arrayOf("+", "＋"),
                    arrayOf(",", "，"),
                    arrayOf("-", "－"),
                    arrayOf(".", "．"),
                    arrayOf("/", "／"),
                    arrayOf(":", "："),
                    arrayOf(";", "；"),
                    arrayOf("<", "＜"),
                    arrayOf("=", "＝"),
                    arrayOf(">", "＞"),
                    arrayOf("?", "？"),
                    arrayOf("@", "＠"),
                    arrayOf("[", "［"),
                    arrayOf("\\", "＼"),
                    arrayOf("]", "］"),
                    arrayOf("^", "＾"),
                    arrayOf("_", "＿"),
                    arrayOf("`", "｀"),
                    arrayOf("{", "｛"),
                    arrayOf("|", "｜"),
                    arrayOf("}", "｝"),
                    arrayOf("~", "～")
            ).collect(Collectors.toMap(
                    { data: Array<String> -> data[0] },
                    { data: Array<String> -> data[1] }))

    /**
     * Turns a string of Small Capitals into normal letters
     */
    fun unFancy(s: String): String {
        var noFancyString = ""
        for (c in s) {
            noFancyString += if (c.toInt() in 0xFF01..0xFF5E) c - 0xFEE0 else c
            // https://en.wikipedia.org/wiki/Halfwidth_and_Fullwidth_Forms_(Unicode_block)
            // later? https://en.wikipedia.org/wiki/Mathematical_Alphanumeric_Symbols
        }
        return noFancyString
    }
}