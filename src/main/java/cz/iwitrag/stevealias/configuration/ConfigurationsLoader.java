package cz.iwitrag.stevealias.configuration;

import cz.iwitrag.stevealias.SteveAliasBungeePlugin;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EverythingIsNonnullByDefault
public class ConfigurationsLoader
{
    private static final String CONFIG_FILENAME = "config.yml";
    private static final String MESSAGES_FILENAME = "messages.yml";
    private static final String ALIASES_FOLDER = "aliases";
    private static final String EXAMPLE_ALIASES_FILENAME = "exampleAliases.yml";
    private static final String PATH_SEPARATOR  = "/"; // I have tried File.separator (= "\") but it didn't work

    public Configurations loadConfigurations(SteveAliasBungeePlugin plugin)
    {
        File dataFolder = plugin.getDataFolder();
        File configFile = new File(dataFolder, CONFIG_FILENAME);
        File messagesFile = new File(dataFolder, MESSAGES_FILENAME);
        File aliasesFolder = new File(dataFolder, ALIASES_FOLDER);

        prepareDirectory(dataFolder);

        // Copy default files from resources to plugin folder
        copyFromResourcesIfNotExists(configFile, getResourcesSupplier(plugin, CONFIG_FILENAME));
        copyFromResourcesIfNotExists(messagesFile, getResourcesSupplier(plugin, MESSAGES_FILENAME));

        // Default example aliases will be copied only if "aliases" directory does not exist
        if (!aliasesFolder.exists())
        {
            prepareDirectory(aliasesFolder);
            File exampleAliasesFile = new File(aliasesFolder, EXAMPLE_ALIASES_FILENAME);
            String exampleAliasesResourcesPath = ALIASES_FOLDER + PATH_SEPARATOR + EXAMPLE_ALIASES_FILENAME;

            copyFromResourcesIfNotExists(exampleAliasesFile, getResourcesSupplier(plugin, exampleAliasesResourcesPath));
        }

        // Files should be present, now load Configuration from them
        ConfigurationProvider bungeeConfigProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        Configuration config = loadConfiguration(bungeeConfigProvider, configFile);
        Configuration messages = loadConfiguration(bungeeConfigProvider, messagesFile);
        List<Configuration> aliasesFiles = new ArrayList<>();

        // Go through "aliases" directory and load all Configurations from there
        File[] filesInAliasesFolder = aliasesFolder.listFiles();
        if (filesInAliasesFolder == null)
            throw new RuntimeException("Failed to load files with aliases");
        for (File aliasFile : filesInAliasesFolder)
        {
            aliasesFiles.add(loadConfiguration(bungeeConfigProvider, aliasFile));
        }

        return new Configurations(config, messages, aliasesFiles);
    }

    private void prepareDirectory(File directory)
    {
        if (!directory.exists() && !directory.mkdir())
        {
            throw new RuntimeException("Failed to create directory " + directory.getAbsolutePath());
        }
    }

    private Supplier<InputStream> getResourcesSupplier(SteveAliasBungeePlugin plugin, String resourcesPath)
    {
        return () -> {
            InputStream resourceAsStream = plugin.getResourceAsStream(resourcesPath);
            if (resourceAsStream == null)
                throw new IllegalStateException("Failed to open Resource " + resourcesPath + " as stream");
            return resourceAsStream;
        };
    }

    private void copyFromResourcesIfNotExists(File target, Supplier<InputStream> inputStreamSupplier)
    {
        if (!target.exists())
        {
            try (FileOutputStream out = new FileOutputStream(target);
                 InputStream in = inputStreamSupplier.get())
            {
                in.transferTo(out);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Failed to copy file " + target.getName() + " from Resources: " + e.getMessage(), e);
            }
        }
    }

    private Configuration loadConfiguration(ConfigurationProvider bungeeConfigProvider, File file)
    {
        try
        {
            return bungeeConfigProvider.load(file);
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to load configuration from file " + file.getName(), e);
        }
    }
}
