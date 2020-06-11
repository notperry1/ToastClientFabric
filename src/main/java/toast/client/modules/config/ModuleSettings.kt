package toast.client.modules.config

import java.util.*

/**
 * Class defining all the settings for a module
 */
class ModuleSettings {
    /**
     * Map containing all of the settings for a module and their names
     */
    var settings: MutableMap<String, Setting> = TreeMap()

    /**
     * Map containing all of the setting definitions for a module and their names
     */
    var settingsDef: MutableMap<String, SettingDef> = TreeMap()

    /**
     * Adds a boolean setting to the map
     */
    fun addBoolean(name: String, defaultValue: Boolean) {
        settings[name] = Setting(defaultValue)
        settingsDef[name] = SettingDef()
    }

    /**
     * Adds a mode setting to the map
     */
    fun addMode(name: String, defaultMode: String, vararg modes: String) {
        settings[name] = Setting(defaultMode)
        val modeList = ArrayList(listOf(*modes))
        settingsDef[name] = SettingDef(modeList)
    }

    /**
     * Adds a value or "slider" type setting to the map
     */
    fun addSlider(name: String, minimumValue: Double, defaultValue: Double, maximumValue: Double) {
        settings[name] = Setting(defaultValue)
        settingsDef[name] = SettingDef(minimumValue, maximumValue)
    }

    /**
     * Gets a setting from the map using it's name
     */
    fun getSetting(name: String): Setting? = settings[name]

    /**
     * Gets a setting's definition from the map using it's name
     */
    fun getSettingDef(name: String): SettingDef? = settingsDef[name]

    /**
     * Gets a the enabled state of a setting using it's name
     */
    fun getBoolean(name: String?): Boolean = settings[name]!!.enabled!!

    /**
     * Gets the current mode of a setting using it's name
     */
    fun getMode(name: String): String? {
        return (settings[name] ?: return null).mode
    }

    /**
     * Gets the current value of a setting using it's name
     */
    fun getValue(name: String?): Double? {
        return (settings[name] ?: return null).value
    }

    /**
     * Gets a list of the valid modes for a setting
     */
    fun getModes(name: String): ArrayList<String>? {
        return (settingsDef[name] ?: return null).modes
    }

    /**
     * Gets the maximum value of a setting
     */
    fun getMax(name: String): Double? {
        return (settingsDef[name] ?: return null).maxValue
    }

    /**
     * Gets the minimum value of a setting
     */
    fun getMin(name: String): Double? {
        return (settingsDef[name] ?: return null).minValue
    }
}