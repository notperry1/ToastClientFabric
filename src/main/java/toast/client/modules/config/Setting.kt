package toast.client.modules.config

import com.google.gson.annotations.SerializedName
import toast.client.ToastClient

/**
 * Class that stores a module's settings
 */
class Setting {
    /**
     * The current mode of the Setting
     */
    @SerializedName("Current Mode")
    var mode: String? = null

    /**
     * Wether or not this setting is enabled
     */
    @SerializedName("Enabled")
    var enabled: Boolean? = null

    /**
     * The current value of this setting
     */
    @SerializedName("Current Value")
    var value: Double? = null

    constructor(mode: String) {
        setNewMode(mode)
    }

    constructor(value: Double) {
        setNewValue(value)
    }

    constructor(enabled: Boolean) {
        setNewEnabled(enabled)
    }

    /**
     * Sets this setting's mode
     */
    fun setNewMode(newMode: String) {
        mode = newMode
        value = null
        enabled = null
        ToastClient.CONFIG_MANAGER.writeConfig()
    }

    /**
     * Sets this setting's value
     */
    fun setNewValue(newValue: Double) {
        mode = null
        value = newValue
        enabled = null
        ToastClient.CONFIG_MANAGER.writeConfig()
    }

    /**
     * Changes the enabled state of this setting
     */
    fun setNewEnabled(enabled: Boolean) {
        mode = null
        value = null
        this.enabled = enabled
        ToastClient.CONFIG_MANAGER.writeConfig()
    }

    /**
     * The type of this setting
     */
    val type: Int
        get() = if (mode != null) 0 else if (value != null) 1 else if (enabled != null) 2 else 3
}