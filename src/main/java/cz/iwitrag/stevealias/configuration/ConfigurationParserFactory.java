package cz.iwitrag.stevealias.configuration;

import net.md_5.bungee.config.Configuration;

public interface ConfigurationParserFactory
{
    ConfigurationParser createParser(Configuration configuration);
}
