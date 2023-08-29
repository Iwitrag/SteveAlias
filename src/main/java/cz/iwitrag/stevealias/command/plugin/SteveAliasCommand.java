package cz.iwitrag.stevealias.command.plugin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import cz.iwitrag.stevealias.PluginReloader;
import cz.iwitrag.stevealias.SteveAliasBungeePlugin;
import cz.iwitrag.stevealias.command.CommandManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

@CommandAlias("stevealias")
public class SteveAliasCommand extends BaseCommand
{
    private final SteveAliasBungeePlugin plugin;
    private final PluginReloader pluginReloader;
    private final CommandManager commandManager;

    @Inject
    public SteveAliasCommand(SteveAliasBungeePlugin plugin, PluginReloader pluginReloader, CommandManager commandManager)
    {
        this.plugin = plugin;
        this.pluginReloader = pluginReloader;
        this.commandManager = commandManager;
    }

    @Default
    @CatchUnknown
    @Subcommand("info")
    @Description("Basic plugin command")
    public void infoCommand(CommandSender sender)
    {
        sender.sendMessage(new ComponentBuilder()
                .append("Running plugin ")
                .color(ChatColor.GOLD)
                .append(String.format("%s v%s",
                        plugin.getDescription().getName(),
                        plugin.getDescription().getVersion()
                ))
                .color(ChatColor.YELLOW)
                .create());
        informAboutCommands(sender);
    }

    @Subcommand("reload")
    @Description("Reloads all config files and aliases")
    public void reloadCommand(CommandSender sender)
    {
        sender.sendMessage(new ComponentBuilder()
                .append("Reloading config files and aliases...")
                .color(ChatColor.GOLD)
                .create());
        pluginReloader.reloadPlugin(plugin, commandManager);
        sender.sendMessage(new ComponentBuilder()
                .append("Plugin was reloaded!")
                .color(ChatColor.GREEN)
                .create());
        informAboutCommands(sender);
    }

    private void informAboutCommands(CommandSender sender)
    {
        sender.sendMessage(new ComponentBuilder()
                .append("Plugin is handling ")
                .color(ChatColor.GRAY)
                .append(String.format("%d command aliases", commandManager.getCommands().size()))
                .color(ChatColor.WHITE)
                .append(" for you.")
                .color(ChatColor.GRAY)
                .create());
    }
}
