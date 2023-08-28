package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.md_5.bungee.api.CommandSender;

import java.util.List;

@EverythingIsNonnullByDefault
public interface CommandOperation
{
    void execute(CommandSender sender, List<String> arguments);
}
