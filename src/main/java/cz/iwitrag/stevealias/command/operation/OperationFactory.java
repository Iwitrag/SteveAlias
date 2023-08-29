package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Optional;

@EverythingIsNonnullByDefault
public interface OperationFactory
{
    Optional<CommandOperation> getOperation(String operation);
}
