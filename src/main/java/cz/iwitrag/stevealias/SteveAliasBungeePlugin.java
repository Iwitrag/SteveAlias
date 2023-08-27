package cz.iwitrag.stevealias;

import cz.iwitrag.stevealias.configuration.ConfigurationsLoader;
import cz.iwitrag.stevealias.configuration.Configurations;
import cz.iwitrag.stevealias.listener.CommandListener;
import cz.iwitrag.stevealias.listener.TabCompleteListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class SteveAliasBungeePlugin extends Plugin
{
    private Configurations configurations;

    @Override
    public void onEnable()
    {
        super.onEnable();
        registerListeners();
        configurations = loadConfigurations();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
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
