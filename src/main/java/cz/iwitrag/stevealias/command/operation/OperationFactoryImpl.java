package cz.iwitrag.stevealias.command.operation;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.text.PlaceholderHandler;

import java.util.Optional;

@EverythingIsNonnullByDefault
public class OperationFactoryImpl implements OperationFactory
{
    private static final String MESSAGE_OPERATION_PREFIX = "msg ";

    private final PlaceholderHandler placeholderHandler;

    @Inject
    public OperationFactoryImpl(PlaceholderHandler placeholderHandler)
    {
        this.placeholderHandler = placeholderHandler;
    }

    @Override
    public Optional<CommandOperation> getOperation(String operation)
    {
        String operationLower = operation.toLowerCase();
        if (operationLower.startsWith(MESSAGE_OPERATION_PREFIX))
        {
            return Optional.of(new MessageOperation(placeholderHandler, operation.substring(MESSAGE_OPERATION_PREFIX.length())));
        }
        return Optional.empty();
    }
}
