package cz.iwitrag.stevealias;

import cz.iwitrag.stevealias.command.CommandManager;

public interface PluginReloader
{
    void reloadPlugin(SteveAliasBungeePlugin plugin, CommandManager manager);
}
