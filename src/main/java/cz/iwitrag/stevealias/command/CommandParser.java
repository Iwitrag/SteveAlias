package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.event.ChatEvent;

import java.util.Set;

@EverythingIsNonnullByDefault
public interface CommandParser
{
    /** Creates TextCommand from chat message as it comes from {@link ChatEvent#getMessage()} */
    TextCommand parseCommand(String chatMessage);

    /** Parses number or range of numbers separated by "-" or just symbol "_" alone. Numeric values smaller than zero are ignored. */
    Set<Integer> parseArgumentsAmount(String amount);

    /** Parses aliases separated by "|" */
    Set<String> parseAliases(String command);
}
