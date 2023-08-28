package cz.iwitrag.stevealias.command.operation;

import java.util.Optional;

public interface OperationFactory
{
    Optional<CommandOperation> getOperation(String operation);
}
