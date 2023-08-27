package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.operation.CommandOperation;

import java.util.*;
import java.util.stream.Collectors;

@EverythingIsNonnullByDefault
public class SteveCommand
{
    public static final int ANY_AMOUNT_OF_ARGS = -1;

    private final String alias;
    private final Map<Integer, List<CommandOperation>> operations;

    public SteveCommand(String alias, Map<Integer, List<CommandOperation>> operations)
    {
        this.alias = alias;
        this.operations = Collections.unmodifiableMap(
                operations.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Collections.unmodifiableList(entry.getValue())
                ))
        );
    }

    public String getAlias()
    {
        return alias;
    }

    public Map<Integer, List<CommandOperation>> getOperations()
    {
        return operations;
    }

    /**
     * Returns operations to be executed for given amount of arguments
     * @return Operations to be executed or empty list if no operations are defined for this amount of arguments
     */
    public List<CommandOperation> getOperations(int arguments)
    {
        return operations.containsKey(arguments)
                ? operations.get(arguments)
                : List.of();
    }
}
