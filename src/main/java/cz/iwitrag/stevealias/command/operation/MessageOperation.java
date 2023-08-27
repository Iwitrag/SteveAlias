package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

@EverythingIsNonnullByDefault
public class MessageOperation implements CommandOperation
{
    private final String message;

    public MessageOperation(String message)
    {
        this.message = message;
    }

    @Override
    public void execute()
    {
        // TODO - MessageOperation - message sender, replace %sender_name% with sender name
    }
}
