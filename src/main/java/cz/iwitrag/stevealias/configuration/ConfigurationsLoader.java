package cz.iwitrag.stevealias.configuration;

import cz.iwitrag.stevealias.SteveAliasBungeePlugin;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

@EverythingIsNonnullByDefault
public interface ConfigurationsLoader
{
    Configurations loadConfigurations(SteveAliasBungeePlugin plugin);
}
