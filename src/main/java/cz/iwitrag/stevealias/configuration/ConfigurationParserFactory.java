package cz.iwitrag.stevealias.configuration;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.config.Configuration;

@EverythingIsNonnullByDefault
public interface ConfigurationParserFactory
{
    ConfigurationParser createParser(Configuration configuration);
}
