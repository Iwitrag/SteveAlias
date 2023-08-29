package cz.iwitrag.stevealias;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.SteveCommand;
import cz.iwitrag.stevealias.configuration.ConfigurationParserFactory;
import cz.iwitrag.stevealias.configuration.Configurations;
import cz.iwitrag.stevealias.configuration.ConfigurationsLoader;

import java.util.Set;
import java.util.stream.Collectors;

public class PluginReloaderImpl implements PluginReloader
{
    private final ConfigurationsLoader configurationsLoader;
    private final ConfigurationParserFactory configurationParserFactory;
    private final CommandManager commandManager;

    @Inject
    public PluginReloaderImpl(ConfigurationsLoader configurationsLoader, ConfigurationParserFactory configurationParserFactory, CommandManager commandManager)
    {
        this.configurationsLoader = configurationsLoader;
        this.configurationParserFactory = configurationParserFactory;
        this.commandManager = commandManager;
    }

    @Override
    public void reloadPlugin(SteveAliasBungeePlugin plugin, CommandManager manager)
    {
        manager.clearCommands();
        Configurations configurations = configurationsLoader.loadConfigurations(plugin);
        Set<SteveCommand> configuredCommands = parseConfigurations(configurations, configurationParserFactory);
        configuredCommands.forEach(commandManager::registerCommand);
    }

    private Set<SteveCommand> parseConfigurations(Configurations configurations, ConfigurationParserFactory configurationParserFactory)
    {
        return configurations.aliases().stream()
                .map(config -> configurationParserFactory.createParser(config).parseCommands())
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
