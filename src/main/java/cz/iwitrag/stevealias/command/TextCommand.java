package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Collections;
import java.util.List;

@EverythingIsNonnullByDefault
public class TextCommand
{
    private final String commandWithSlash;
    private final List<String> arguments;

    private static final String SLASH = "/";

    public TextCommand(String commandWithSlash, List<String> arguments)
    {
        this.commandWithSlash = commandWithSlash;
        this.arguments = Collections.unmodifiableList(arguments);
    }

    public String getCommandWithSlash()
    {
        return commandWithSlash;
    }

    public String getCommandWithoutSlash()
    {
        return commandWithSlash.substring(SLASH.length());
    }

    public List<String> getArguments()
    {
        return arguments;
    }
}
