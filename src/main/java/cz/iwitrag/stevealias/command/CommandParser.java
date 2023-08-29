package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.event.ChatEvent;

import java.util.List;
import java.util.Set;

@EverythingIsNonnullByDefault
public interface CommandParser
{
    String ARGS_RANGE_SEPARATOR = "-";
    String ANY_AMOUNT_OF_ARGS = "_";
    String COMMAND_ALIAS_SEPARATOR = "|";
    String WORDS_SEPARATOR = " ";

    /** Creates TextCommand from chat message as it comes from {@link ChatEvent#getMessage()} */
    TextCommand parseCommand(String chatMessage);

    /**
     * Parses number or range of numbers separated by "-" or just symbol "_" alone.
     * Any negative number will cause to return empty List. Reversed range will be corrected. Returned integers will be in ascending order.
     */
    List<Integer> parseArgumentsAmount(String amount);

    /** Parses aliases separated by "|" */
    Set<String> parseAliases(String command);
}
