package cz.iwitrag.stevealias;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.CommandManagerImpl;
import cz.iwitrag.stevealias.command.CommandParser;
import cz.iwitrag.stevealias.command.CommandParserImpl;
import cz.iwitrag.stevealias.command.operation.OperationFactory;
import cz.iwitrag.stevealias.command.operation.OperationFactoryImpl;
import cz.iwitrag.stevealias.configuration.*;

public class ServicesModule extends AbstractModule
{
    private SteveAliasBungeePlugin plugin;

    public ServicesModule(SteveAliasBungeePlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    protected void configure()
    {
        bind(SteveAliasBungeePlugin.class).toInstance(plugin);
        bind(CommandManager.class).to(CommandManagerImpl.class).in(Scopes.SINGLETON);
        bind(CommandParser.class).to(CommandParserImpl.class);
        bind(ConfigurationsLoader.class).to(ConfigurationsLoaderImpl.class);
        bind(OperationFactory.class).to(OperationFactoryImpl.class);
        bind(PluginReloader.class).to(PluginReloaderImpl.class);
        install(new FactoryModuleBuilder().implement(ConfigurationParser.class, ConfigurationParserImpl.class).build(ConfigurationParserFactory.class));
    }
}
