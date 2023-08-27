package cz.iwitrag.stevealias.listener;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@EverythingIsNonnullByDefault
public class CommandListener implements Listener
{
    @EventHandler
    public void onChatEvent(ChatEvent e)
    {
        if (e.getSender() instanceof CommandSender sender)
        {
            // TODO - CommandListener - check if command is registered and execute it
            // TODO - CommandListener - what about console sender?
        }
    }
}
