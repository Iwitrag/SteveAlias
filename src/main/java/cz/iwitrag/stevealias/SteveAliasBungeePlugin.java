package cz.iwitrag.stevealias;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.configuration.ConfigurationsLoader;
import cz.iwitrag.stevealias.configuration.Configurations;
import cz.iwitrag.stevealias.configuration.ConfigurationParser;
import cz.iwitrag.stevealias.listener.CommandListener;
import cz.iwitrag.stevealias.listener.TabCompleteListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;

@EverythingIsNonnullByDefault
public class SteveAliasBungeePlugin extends Plugin
{
    private static SteveAliasBungeePlugin instance;
    private Configurations configurations;
    private CommandManager commandManager;

    @Override
    public void onEnable()
    {
        super.onEnable();
        instance = this;
        registerListeners();
        configurations = loadConfigurations();
        commandManager = parseConfigurations(configurations);
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

    public CommandManager getCommandManager()
    {
        return commandManager;
    }

    private void registerListeners()
    {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerListener(this, new CommandListener());
        pluginManager.registerListener(this, new TabCompleteListener());
    }

    private Configurations loadConfigurations()
    {
        return new ConfigurationsLoader().loadConfigurations(this);
    }

    private CommandManager parseConfigurations(Configurations configurations)
    {
        CommandManager commandManager = new CommandManager();
        configurations.aliases().forEach(config -> registerCommandsFromConfiguration(config, commandManager));
        return commandManager;
    }

    private void registerCommandsFromConfiguration(Configuration configuration, CommandManager commandManager)
    {
        new ConfigurationParser(configuration)
                .parseCommands()
                .forEach(commandManager::registerCommand);
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
