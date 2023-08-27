package cz.iwitrag.stevealias.listener;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@EverythingIsNonnullByDefault
public class TabCompleteListener implements Listener
{
    @EventHandler
    public void onTabComplete(TabCompleteEvent e)
    {
        if (e.getSender() instanceof CommandSender sender)
        {

        }
    }
}
