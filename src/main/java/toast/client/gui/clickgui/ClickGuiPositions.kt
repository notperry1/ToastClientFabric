package toast.client.gui.clickgui

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import toast.client.ToastClient.FILE_MANAGER
import toast.client.modules.Module
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Class to help store positions and states of ClickGUI category windows
 */
class ClickGuiPositions {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val clickGuiPositionsFile = File("toastclient/clickgui.json")

    /**
     * Writes the ClickGUI's state to a file
     */
    fun writePositions() {
        FILE_MANAGER.writeFile(clickGuiPositionsFile, gson.toJson(positions))
    }

    /**
     * Loads the ClickGUI's state from a file
     */
    fun loadPositions() {
        val loadPositions: LinkedHashMap<Module.Category, Position>? = gson.fromJson(FileReader(FILE_MANAGER.createFile(clickGuiPositionsFile)), object : TypeToken<LinkedHashMap<Module.Category, Position>>() {}.type)
        if (loadPositions != null) positions = loadPositions
    }

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

    companion object {
        /**
         * Map containing the positions of each category window
         */
        @JvmStatic
        var positions: LinkedHashMap<Module.Category, Position> = LinkedHashMap()
    }
}