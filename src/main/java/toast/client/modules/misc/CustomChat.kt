package toast.client.modules.misc

import com.google.common.eventbus.Subscribe
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket
import net.minecraft.text.LiteralText
import toast.client.ToastClient
import toast.client.events.network.EventPacketReceived
import toast.client.events.network.EventPacketSent
import toast.client.modules.Module
import toast.client.utils.FancyChatUtil
import toast.client.utils.FancyChatUtil.unFancy

/**
 * Module that modifies chat messages
 */
class CustomChat : Module("CustomChat", "Custom chat messages", Category.MISC, -1) {
    private var isMadeByCustomChat = false
    override fun onEnable() {
        isMadeByCustomChat = true
    }

    @Subscribe
    fun onSend(e: EventPacketSent) {
        if (mc.player == null) return
        if (e.getPacket() is ChatMessageC2SPacket) {
            if (!getBool("Custom Suffix")) suffix = "ᴛᴏᴀѕᴛᴄʟɪᴇɴᴛ"
            val packetMessage = (e.getPacket() as ChatMessageC2SPacket).chatMessage
            var message = packetMessage
            if (packetMessage.startsWith(ToastClient.cmdPrefix) || packetMessage.startsWith("/") && !getBool("Commands")) return
            if (getBool("Fancy Chat")) {
                when (settings.getMode("Fancy chat type")) {
                    "Rainbow" -> message = FancyChatUtil.rainbowText(packetMessage)
                    "Classic" -> message = FancyChatUtil.classicFancy(packetMessage)
                    "Retard" -> message = FancyChatUtil.retardChat(packetMessage)
                    "Spaced" -> message = FancyChatUtil.spaces(packetMessage)
                }
            }
            when (settings.getMode("Separator")) {
                "None" -> message += " $suffix"
                "Default" -> message += " | $suffix"
                "Brackets" -> message += " < $suffix > "
            }
            isMadeByCustomChat = !isMadeByCustomChat
            if (isMadeByCustomChat) return
            e.isCancelled = true
            (mc.player ?: return).sendChatMessage(message)
        }
    }

    @Subscribe
    fun onRecv(e: EventPacketReceived) {
        if (getBool("Anti Fancy")) {
            if (e.getPacket() is ChatMessageS2CPacket) {
                (mc.player
                        ?: return).addChatMessage(LiteralText(unFancy((e.getPacket() as ChatMessageS2CPacket).message.asFormattedString())), false)
                e.isCancelled = true
            }
        }
    }

    companion object {
        /**
         * The suffix to add to messages
         */
        @JvmField
        var suffix: String = ""
    }

    init {
        settings.addBoolean("Fancy Chat", false)
        settings.addBoolean("Commands", false)
        settings.addBoolean("Custom Suffix", false)
        settings.addMode("Separator", "None", "None", "Default", "Brackets")
        settings.addMode("Fancy chat type", "Classic", "Classic", "Retard", "Rainbow", "Spaced")
        settings.addBoolean("Anti Fancy", false)
    }
}