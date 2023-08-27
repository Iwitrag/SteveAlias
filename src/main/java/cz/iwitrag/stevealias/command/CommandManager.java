package cz.iwitrag.stevealias.command;

import java.util.HashSet;
import java.util.Set;

/** Serves as database of known commands - both from aliases config and from plugin itself */
public class CommandManager
{
    private Set<SteveCommand> registeredCommands = new HashSet<>();
}
