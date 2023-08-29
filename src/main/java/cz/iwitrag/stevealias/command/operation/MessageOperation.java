package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.text.PlaceholderHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;

@EverythingIsNonnullByDefault
public class MessageOperation implements CommandOperation
{
    private final PlaceholderHandler placeholderHandler;
    private final String message;

    public MessageOperation(PlaceholderHandler placeholderHandler, String message)
    {
        this.placeholderHandler = placeholderHandler;
        this.message = message;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments)
    {
        String messageToSend = placeholderHandler.replaceSenderNamePlaceholder(message, sender.getName());
        messageToSend = placeholderHandler.replaceArgumentsPlaceholders(messageToSend, arguments);
        messageToSend = placeholderHandler.replaceColorCodesWithMiniMessageTags(messageToSend);
        Component component = placeholderHandler.parseMiniMessage(messageToSend);
        BaseComponent[] bungeeComponents = BungeeComponentSerializer.get().serialize(component);
        sender.sendMessage(bungeeComponents);
    }


}
