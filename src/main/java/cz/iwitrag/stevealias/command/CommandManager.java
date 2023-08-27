package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/** Serves as database of known commands - both from aliases config and from plugin itself */
@EverythingIsNonnullByDefault
public class CommandManager
{
    private final Map<String, SteveCommand> registeredCommands = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void registerCommand(String alias, SteveCommand command)
    {
        if (registeredCommands.containsKey(alias))
        {
            throw new IllegalArgumentException(String.format("Command %s is already registered.", alias));
        }
        else
        {
            registeredCommands.put(alias, command);
        }
    }

    public Optional<SteveCommand> getCommand(String alias)
    {
        return Optional.ofNullable(registeredCommands.get(alias));
    }
}
