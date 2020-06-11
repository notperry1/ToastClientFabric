package toast.client.gui.clickgui

import com.google.gson.annotations.SerializedName
import toast.client.modules.Module
import java.util.*

/**
 * Class to help store positions and states of ClickGUI category windows
 */
class ClickGuiPositions {
    /**
     * Map containing the positions of each category window
     */
    var positions: EnumMap<Module.Category, Position> = EnumMap(Module.Category::class.java)

    /**
     * Class for containing each category's position and state
     */
    class Position(
            /**
             * X Position of the category
             */
            @SerializedName("X")
            var x: Double,
            /**
             * Y position of the category
             */
            @SerializedName("Y")
            var y: Double,
            /**
             * Whether or not the category is expanded
             */
            @SerializedName("Expanded")
            var expanded: Boolean,
            /**
             * Which modules of the category are expanded
             */
            @SerializedName("Expanded Modules")
            var expandedModule: HashMap<String, Boolean>)
}