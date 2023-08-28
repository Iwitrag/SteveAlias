package cz.iwitrag.stevealias;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.iwitrag.stevealias.command.CommandManager;
import cz.iwitrag.stevealias.command.CommandManagerImpl;
import cz.iwitrag.stevealias.command.operation.OperationFactory;
import cz.iwitrag.stevealias.command.operation.OperationFactoryImpl;
import cz.iwitrag.stevealias.configuration.*;

public class ServicesModule extends AbstractModule
{
    @Override
    protected void configure() {
        bind(CommandManager.class).to(CommandManagerImpl.class).in(Scopes.SINGLETON);
        bind(ConfigurationsLoader.class).to(ConfigurationsLoaderImpl.class);
        bind(OperationFactory.class).to(OperationFactoryImpl.class);
        install(new FactoryModuleBuilder().implement(ConfigurationParser.class, ConfigurationParserImpl.class).build(ConfigurationParserFactory.class));
    }
}
