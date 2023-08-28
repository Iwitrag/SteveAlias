package cz.iwitrag.stevealias.configuration;

import cz.iwitrag.stevealias.command.SteveCommand;

import java.util.Set;

public interface ConfigurationParser
{
    Set<SteveCommand> parseCommands();
}
