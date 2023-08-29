package cz.iwitrag.stevealias;

import co.aikar.commands.BungeeCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.plugin.SteveAliasCommand;
import cz.iwitrag.stevealias.listener.CommandListener;
import cz.iwitrag.stevealias.listener.TabCompleteListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

@EverythingIsNonnullByDefault
public class SteveAliasBungeePlugin extends Plugin
{
    private static Injector injector;

    @Override
    public void onEnable()
    {
        super.onEnable();
        injector = Guice.createInjector(new ServicesModule(this));
        registerListeners();
        registerPluginCommands();
        registerConfiguredCommands();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    private void registerListeners()
    {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerListener(this, injector.getInstance(CommandListener.class));
        pluginManager.registerListener(this, injector.getInstance(TabCompleteListener.class));
    }

    private void registerPluginCommands()
    {
        BungeeCommandManager manager = new BungeeCommandManager(this);
        manager.registerCommand(injector.getInstance(SteveAliasCommand.class));
    }

    private void registerConfiguredCommands()
    {
        injector.getInstance(PluginReloader.class).reloadPlugin(this, injector.getInstance(CommandManager.class));
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

    // TODO - make it possible to execute aliases from console - no listener for that, need to a) make aliases registered commands b) create special command to run aliases from console

}
