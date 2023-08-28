package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Optional;

/** Serves as database of known commands - both from aliases config and from plugin itself */
@EverythingIsNonnullByDefault
public interface CommandManager
{
    void registerCommand(SteveCommand command);

    Optional<SteveCommand> getCommand(String alias);
}
