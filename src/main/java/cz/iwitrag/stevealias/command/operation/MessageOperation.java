package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.List;

@EverythingIsNonnullByDefault
public class MessageOperation implements CommandOperation
{
    private final String message;

    public MessageOperation(String message)
    {
        this.message = message;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments)
    {
        sender.sendMessage(new ComponentBuilder(message).create());
        // TODO - MessageOperation - replace %sender_name% with sender name and command placeholders
    }
}
