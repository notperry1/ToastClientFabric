package toast.client.modules.config

import java.util.*

/**
 * Class that contains the definition of a setting (correct values)
 */
class SettingDef {
    /**
     * Array containing the valid modes for a setting
     */
    val modes: ArrayList<String>?
    private val isBool: Boolean

    /**
     * Maximum value of the setting
     */
    val maxValue: Double?

    /**
     * Minimum value of the setting
     */
    val minValue: Double?

    constructor(modes: ArrayList<String>) {
        this.modes = modes
        isBool = false
        minValue = null
        maxValue = minValue
    }

    constructor(minvalue: Double, maxvalue: Double) {
        modes = null
        isBool = false
        minValue = minvalue
        maxValue = maxvalue
    }

    constructor() {
        modes = null
        isBool = true
        minValue = null
        maxValue = minValue
    }

    /**
     * Type of the setting
     */
    val type: String
        get() = (if (modes != null) "mode" else if (minValue != null && maxValue != null) "value" else if (isBool) "boolean" else null) as String
}