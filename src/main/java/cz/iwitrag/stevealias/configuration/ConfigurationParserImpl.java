package cz.iwitrag.stevealias.configuration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.SteveCommand;
import cz.iwitrag.stevealias.command.operation.CommandOperation;
import cz.iwitrag.stevealias.command.operation.OperationFactory;
import net.md_5.bungee.config.Configuration;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Turns loaded configuration into SteveCommands */
@EverythingIsNonnullByDefault
public class ConfigurationParserImpl implements ConfigurationParser
{
    private static final String COMMAND_ALIAS_SEPARATOR = "|";
    private static final String ARGS_RANGE_SEPARATOR = "-";
    private static final String ANY_AMOUNT_OF_ARGS = "_";
    private static final String CONFIGURATION_PATH_SEPARATOR = ".";

    private final Configuration configuration;
    private final OperationFactory operationFactory;
    private final Set<SteveCommand> parsedCommands = new HashSet<>();

    @Inject
    public ConfigurationParserImpl(@Assisted Configuration configuration, OperationFactory operationFactory)
    {
        this.configuration = configuration;
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
        return parseAliases(aliases).stream()
                .map(alias -> new SteveCommand(alias, operations))
                .collect(Collectors.toSet());
    }

    /** Parses aliases separated by "|" */
    private Set<String> parseAliases(String commandKey)
    {
        return Stream.of(commandKey.split(Pattern.quote(COMMAND_ALIAS_SEPARATOR)))
                .map(String::trim)
                .filter(alias -> !alias.isEmpty())
                .collect(Collectors.toSet());
    }

    /** Gets operations for each argument amount */
    private Map<Integer, List<CommandOperation>> parseOperations(String aliases)
    {
        Map<Integer, List<CommandOperation>> result = new HashMap<>();
        for (String amountKey : configuration.getSection(aliases).getKeys())
        {
            List<CommandOperation> operations = getOperationsForAmountKey(aliases, amountKey);
            parseArgumentsAmountFromKey(amountKey).forEach(argsAmount -> result.put(argsAmount, operations));
        }
        return result;
    }

    /** Gets list of operations for given amount of arguments */
    private List<CommandOperation> getOperationsForAmountKey(String aliases, String numberOfArgs)
    {
        return configuration.getStringList(aliases + CONFIGURATION_PATH_SEPARATOR + numberOfArgs).stream()
                .map(operationFactory::getOperation)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /** Parses keys that are numbers or range of numbers separated by "-" or just symbol "_" alone */
    private Set<Integer> parseArgumentsAmountFromKey(String key)
    {
        Optional<Integer> parsedInteger = parseArgumentsAmount(key);
        if (parsedInteger.isPresent())
            return Set.of(parsedInteger.get());
        if (key.equals(ANY_AMOUNT_OF_ARGS))
            return Set.of(SteveCommand.ANY_AMOUNT_OF_ARGS);
        else
        {
            String[] split = key.split(ARGS_RANGE_SEPARATOR);
            if (split.length != 2)
                return Set.of();
            Optional<Integer> parsedFirst = parseArgumentsAmount(split[0]);
            Optional<Integer> parsedSecond = parseArgumentsAmount(split[1]);
            if (parsedFirst.isEmpty() || parsedSecond.isEmpty())
                return Set.of();
            int from = Math.min(parsedFirst.get(), parsedSecond.get());
            int to = Math.max(parsedFirst.get(), parsedSecond.get());
            Set<Integer> result = new HashSet<>();
            for (int i = from; i <= to; i++)
                result.add(i);
            return result;
        }
    }

    /** Turns String into Integer but only if >= 0 */
    private Optional<Integer> parseArgumentsAmount(String input)
    {
        try
        {
            int parsed = Integer.parseInt(input);
            return parsed >= 0 ? Optional.of(parsed) : Optional.empty();
        }
        catch (NumberFormatException e)
        {
            return Optional.empty();
        }
    }

    // TODO - ConfigurationParser - add better error checking and logs for user
}
