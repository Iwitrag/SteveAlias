package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EverythingIsNonnullByDefault
public class CommandManagerImpl implements CommandManager
{
    private final Set<SteveCommand> registeredCommands = new HashSet<>();

    public void registerCommand(SteveCommand command)
    {
        if (registeredCommands.contains(command))
        {
            throw new IllegalArgumentException(String.format("Command %s is already registered.", command.getAlias()));
        }
        else
        {
            registeredCommands.add(command);
        }
    }

    public Optional<SteveCommand> getCommand(String alias)
    {
        return registeredCommands.stream()
                .filter(command -> command.getAlias().equalsIgnoreCase(alias))
                .findFirst();
    }

    @Override
    public Set<SteveCommand> getCommands()
    {
        return Collections.unmodifiableSet(registeredCommands);
    }

    @Override
    public void clearCommands()
    {
        registeredCommands.clear();
    }
}
