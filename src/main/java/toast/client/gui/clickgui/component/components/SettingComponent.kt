package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.component.Component
import toast.client.modules.config.Setting
import toast.client.modules.config.SettingDef

/**
 * Basic setting component
 */
abstract class SettingComponent : Component() {
    override fun render() = drawBox(" > ", settingName)

    override var subComponents: ArrayList<Component> = ArrayList()

    /**
     * Setting to render
     */
    abstract var setting: Setting

    /**
     * Definition of setting to redner
     */
    abstract var settingDef: SettingDef

    /**
     * Name of setting to render
     */
    abstract var settingName: String

    abstract override var width: Double
}