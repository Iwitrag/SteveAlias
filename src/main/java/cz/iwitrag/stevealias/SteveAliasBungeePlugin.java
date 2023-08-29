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
    private final Injector injector;

    public SteveAliasBungeePlugin()
    {
        this.injector = Guice.createInjector(new ServicesModule(this));
    }

    @Override
    public void onEnable()
    {
        registerListeners();
        registerPluginCommands();
        registerConfiguredCommands();
    }

    @Override
    public void onDisable()
    {
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
    // - seznam aliasů
    // - informace o konkrétním aliasu

    // TODO KONFIGURÁKY
    // - messages (např. pro reload zprávy)
    // - základní config soubor

    // TODO SPUŠTĚNÍ NA BUKKITU
    // - detekce kde je plugin spuštěn
    // - pokud je backend server v bungeecord režimu, vypnout se
    // - ACF pro paper pokud plugin běží na bukkitu

    // TODO složitější alias konfigurace a operace
    // TODO PLACEHOLDER API

    // TODO - make it possible to execute aliases from console - no listener for that, need to a) make aliases registered commands b) create special command to run aliases from console

}
