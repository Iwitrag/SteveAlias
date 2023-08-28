package cz.iwitrag.stevealias.listener;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@EverythingIsNonnullByDefault
public class TabCompleteListener implements Listener
{
    private final CommandManager commandManager;

    @Inject
    public TabCompleteListener(CommandManager commandManager)
    {
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e)
    {
        if (e.getSender() instanceof CommandSender sender)
        {
            // TODO - TabCompleteListener
        }
    }
}
