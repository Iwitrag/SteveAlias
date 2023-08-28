package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@EverythingIsNonnullByDefault
public class TextCommand
{
    private final String commandWithSlash;
    private final List<String> arguments;

    private static final String SLASH = "/";
    private static final String WORDS_SEPARATOR = " ";

    private TextCommand(String commandWithSlash, List<String> arguments)
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

    public static TextCommand parseCommand(String chatMessage)
    {
        List<String> words = Stream.of(chatMessage.split(Pattern.quote(WORDS_SEPARATOR)))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return new TextCommand(words.get(0), words.subList(1, words.size()));
    }
}
