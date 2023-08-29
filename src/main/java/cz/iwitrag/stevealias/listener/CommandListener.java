package cz.iwitrag.stevealias.listener;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.CommandParser;
import cz.iwitrag.stevealias.command.TextCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@EverythingIsNonnullByDefault
public class CommandListener implements Listener
{
    private final CommandParser commandParser;
    private final CommandManager commandManager;

    @Inject
    public CommandListener(CommandParser commandParser, CommandManager commandManager)
    {
        this.commandParser = commandParser;
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onChatEvent(ChatEvent e)
    {
        if (e.getSender() instanceof CommandSender sender && e.isCommand())
        {
            TextCommand textCmd = commandParser.parseCommand(e.getMessage());
            commandManager.getCommand(textCmd.getCommandWithoutSlash()).ifPresent(command -> {
                e.setCancelled(true);
                command.getOperations(textCmd.getArguments().size())
                        .forEach(operation -> operation.execute(sender, textCmd.getArguments()));
            });
        }
    }
}
