package cz.iwitrag.stevealias.configuration;

import net.md_5.bungee.config.Configuration;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record Configurations(Configuration config, Configuration messages, List<Configuration> aliases)
{
    public Configurations
    {
        requireNonNull(config);
        requireNonNull(messages);
        requireNonNull(aliases);
    }
}
