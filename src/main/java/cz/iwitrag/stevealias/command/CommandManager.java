package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Optional;
import java.util.Set;

/** Serves as database of known commands - both from aliases config and from plugin itself */
@EverythingIsNonnullByDefault
public interface CommandManager
{
    void registerCommand(SteveCommand command);

    Optional<SteveCommand> getCommand(String alias);

    Set<SteveCommand> getCommands();

    void clearCommands();
}
