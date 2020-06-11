package toast.client.gui.clickgui.component.components

import toast.client.modules.config.Setting
import toast.client.modules.config.SettingDef

/**
 * Render definition for slider settings
 */
class ComponentSlider(override var setting: Setting, override var settingDef: SettingDef, override var settingName: String, override var x: Double, override var y: Double, override var width: Double) : SettingComponent() {
    init {
        height = textHeightWithSpacing * 2 - 2.0
    }
}