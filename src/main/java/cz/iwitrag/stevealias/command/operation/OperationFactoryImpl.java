package cz.iwitrag.stevealias.command.operation;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandParser;

import java.util.Optional;

@EverythingIsNonnullByDefault
public class OperationFactoryImpl implements OperationFactory
{
    private static final String MESSAGE_OPERATION_PREFIX = "msg ";

    private final CommandParser commandParser;

    @Inject
    public OperationFactoryImpl(CommandParser commandParser)
    {
        this.commandParser = commandParser;
    }

    @Override
    public Optional<CommandOperation> getOperation(String operation)
    {
        String operationLower = operation.toLowerCase();
        if (operationLower.startsWith(MESSAGE_OPERATION_PREFIX))
        {
            return Optional.of(new MessageOperation(commandParser, operation.substring(MESSAGE_OPERATION_PREFIX.length())));
        }
        return Optional.empty();
    }
}
