package cz.iwitrag.stevealias;

import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.SteveCommand;
import cz.iwitrag.stevealias.configuration.ConfigurationParserFactory;
import cz.iwitrag.stevealias.configuration.Configurations;
import cz.iwitrag.stevealias.configuration.ConfigurationsLoader;
import cz.iwitrag.stevealias.listener.CommandListener;
import cz.iwitrag.stevealias.listener.TabCompleteListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Set;
import java.util.stream.Collectors;

@EverythingIsNonnullByDefault
public class SteveAliasBungeePlugin extends Plugin
{
    private static SteveAliasBungeePlugin instance;
    private static Injector injector;
    private Configurations configurations;

    @Override
    public void onEnable()
    {
        super.onEnable();
        instance = this;
        injector = Guice.createInjector(new ServicesModule());
        registerListeners();
        configurations = loadConfigurations(injector.getInstance(ConfigurationsLoader.class));
        Set<SteveCommand> configuredCommands = parseConfigurations(configurations, injector.getInstance(ConfigurationParserFactory.class));
        registerCommands(configuredCommands, injector.getInstance(CommandManager.class));
    }

    public static SteveAliasBungeePlugin getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    public Configurations getConfigurations()
    {
        return configurations;
    }

    private void registerListeners()
    {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerListener(this, injector.getInstance(CommandListener.class));
        pluginManager.registerListener(this, injector.getInstance(TabCompleteListener.class));
    }

    private Configurations loadConfigurations(ConfigurationsLoader loader)
    {
        return loader.loadConfigurations(this);
    }

    private Set<SteveCommand> parseConfigurations(Configurations configurations, ConfigurationParserFactory configurationParserFactory)
    {
        return configurations.aliases().stream()
                .map(config -> configurationParserFactory.createParser(config).parseCommands())
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private void registerCommands(Set<SteveCommand> commands, CommandManager commandManager)
    {
        commands.forEach(commandManager::registerCommand);
    }

    // TODO PŘÍKAZY
    // - základní příkaz
    // - reload
    // - seznam aliasů
    // - informace o konkrétním aliasu

    // TODO KONFIGURÁKY
    // - messages
    // - základní config soubor
    // - složka s configama pro aliasy

    // TODO SPUŠTĚNÍ NA BUKKITU
    // - detekce kde je plugin spuštěn
    // - pokud je backend server v bungeecord režimu, vypnout se

    // TODO ALIASY
    // TODO - barvy, minimessages
    // TODO PLACEHOLDER API

}
