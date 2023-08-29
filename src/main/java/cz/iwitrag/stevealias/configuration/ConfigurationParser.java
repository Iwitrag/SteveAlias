package cz.iwitrag.stevealias.configuration;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.SteveCommand;

import java.util.Set;

@EverythingIsNonnullByDefault
public interface ConfigurationParser
{
    Set<SteveCommand> parseCommands();
}
