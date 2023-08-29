package cz.iwitrag.stevealias.configuration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandParser;
import cz.iwitrag.stevealias.command.SteveCommand;
import cz.iwitrag.stevealias.command.operation.CommandOperation;
import cz.iwitrag.stevealias.command.operation.OperationFactory;
import net.md_5.bungee.config.Configuration;

import java.util.*;
import java.util.stream.Collectors;

/** Turns loaded configuration into SteveCommands */
@EverythingIsNonnullByDefault
public class ConfigurationParserImpl implements ConfigurationParser
{
    private static final String CONFIGURATION_PATH_SEPARATOR = ".";

    private final Configuration configuration;
    private final CommandParser commandParser;
    private final OperationFactory operationFactory;
    private final Set<SteveCommand> parsedCommands = new HashSet<>();

    @Inject
    public ConfigurationParserImpl(@Assisted Configuration configuration, CommandParser commandParser, OperationFactory operationFactory)
    {
        this.configuration = configuration;
        this.commandParser = commandParser;
        this.operationFactory = operationFactory;
    }

    @Override
    public Set<SteveCommand> parseCommands()
    {
        if (!parsedCommands.isEmpty())
        {
            return parsedCommands;
        }
        Set<SteveCommand> commands = configuration.getKeys().stream()
                .map(this::parseCommand)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        parsedCommands.addAll(commands);
        return parsedCommands;
    }

    private Set<SteveCommand> parseCommand(String aliases)
    {
        Map<Integer, List<CommandOperation>> operations = parseOperations(aliases);
        return commandParser.parseAliases(aliases).stream()
                .map(alias -> new SteveCommand(alias, operations))
                .collect(Collectors.toSet());
    }

    /** Gets operations for each argument amount */
    private Map<Integer, List<CommandOperation>> parseOperations(String aliases)
    {
        Map<Integer, List<CommandOperation>> result = new HashMap<>();
        for (String amountKey : configuration.getSection(aliases).getKeys())
        {
            List<CommandOperation> operations = getOperationsForAmountKey(aliases, amountKey);
            commandParser.parseArgumentsAmount(amountKey).forEach(argsAmount -> result.put(argsAmount, operations));
        }
        return result;
    }

    /** Gets list of operations for given amount of arguments */
    private List<CommandOperation> getOperationsForAmountKey(String aliases, String numberOfArgs)
    {
        String path = aliases + CONFIGURATION_PATH_SEPARATOR + numberOfArgs;
        List<String> ops = configuration.getStringList(path);
        if (ops.isEmpty())
            ops = List.of(configuration.getString(path));
        return ops.stream()
                .map(operationFactory::getOperation)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    // TODO - ConfigurationParser - add better error checking and logs for user
}
