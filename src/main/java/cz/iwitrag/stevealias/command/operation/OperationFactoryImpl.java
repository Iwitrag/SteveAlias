package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Optional;

@EverythingIsNonnullByDefault
public class OperationFactoryImpl implements OperationFactory
{
    private static final String MESSAGE_OPERATION_PREFIX = "msg ";

    @Override
    public Optional<CommandOperation> getOperation(String operation)
    {
        String operationLower = operation.toLowerCase();
        if (operationLower.startsWith(MESSAGE_OPERATION_PREFIX))
        {
            return Optional.of(new MessageOperation(operation.substring(MESSAGE_OPERATION_PREFIX.length())));
        }
        return Optional.empty();
    }
}
