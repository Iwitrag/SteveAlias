package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.operation.CommandOperation;

import java.util.*;
import java.util.stream.Collectors;

@EverythingIsNonnullByDefault
public class SteveCommand
{
    private final String alias;
    private final Map<Integer, List<CommandOperation>> operations;

    private SteveCommand(Builder builder)
    {
        this.alias = builder.alias;
        this.operations = Collections.unmodifiableMap(
                builder.operations.entrySet().stream()
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

    public static final class Builder
    {
        private final String alias;
        private final Map<Integer, List<CommandOperation>> operations = new HashMap<>();

        public Builder(String alias)
        {
            this.alias = alias;
        }

        public Builder addOperation(int arguments, CommandOperation operation)
        {
            if (arguments < 0)
            {
                throw new IllegalArgumentException("Amount of arguments cannot be negative");
            }
            List<CommandOperation> operationsForAmount = operations.computeIfAbsent(arguments, i -> new ArrayList<>());
            operationsForAmount.add(operation);
            return this;
        }

        public SteveCommand build()
        {
            return new SteveCommand(this);
        }
    }
}
