package cz.iwitrag.stevealias.command;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EverythingIsNonnullByDefault
public class CommandParserImpl implements CommandParser
{
    private static final String ARGS_RANGE_SEPARATOR = "-";
    private static final String ANY_AMOUNT_OF_ARGS = "_";
    private static final String COMMAND_ALIAS_SEPARATOR = "|";
    private static final String WORDS_SEPARATOR = " ";

    @Override
    public TextCommand parseCommand(String chatMessage)
    {
        List<String> words = Stream.of(chatMessage.split(Pattern.quote(WORDS_SEPARATOR)))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return new TextCommand(words.get(0), words.subList(1, words.size()));
    }

    @Override
    public Set<Integer> parseArgumentsAmount(String key)
    {
        Optional<Integer> parsedInteger = parseNonNegativeInt(key);
        if (parsedInteger.isPresent())
            return Set.of(parsedInteger.get());
        if (key.equals(ANY_AMOUNT_OF_ARGS))
            return Set.of(SteveCommand.ANY_AMOUNT_OF_ARGS);
        else
        {
            String[] split = key.split(ARGS_RANGE_SEPARATOR);
            if (split.length != 2)
                return Set.of();
            Optional<Integer> parsedFirst = parseNonNegativeInt(split[0]);
            Optional<Integer> parsedSecond = parseNonNegativeInt(split[1]);
            if (parsedFirst.isEmpty() || parsedSecond.isEmpty())
                return Set.of();
            int from = Math.min(parsedFirst.get(), parsedSecond.get());
            int to = Math.max(parsedFirst.get(), parsedSecond.get());
            Set<Integer> result = new HashSet<>();
            for (int i = from; i <= to; i++)
                result.add(i);
            return result;
        }
    }

    /** Turns String into Integer but only if >= 0 */
    private Optional<Integer> parseNonNegativeInt(String input)
    {
        try
        {
            int parsed = Integer.parseInt(input);
            return parsed >= 0 ? Optional.of(parsed) : Optional.empty();
        }
        catch (NumberFormatException e)
        {
            return Optional.empty();
        }
    }

    @Override
    public Set<String> parseAliases(String command)
    {
        return Stream.of(command.split(Pattern.quote(COMMAND_ALIAS_SEPARATOR)))
                .map(String::trim)
                .filter(alias -> !alias.isEmpty())
                .collect(Collectors.toSet());
    }
}
