package cz.iwitrag.stevealias.text;

import com.google.inject.Inject;
import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandParser;
import cz.iwitrag.stevealias.command.SteveCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@EverythingIsNonnullByDefault
public class PlaceholderHandlerImpl implements PlaceholderHandler
{
    private final CommandParser commandParser;
    private final MiniMessage miniMessage;

    @Inject
    public PlaceholderHandlerImpl(CommandParser commandParser, MiniMessage miniMessage)
    {
        this.commandParser = commandParser;
        this.miniMessage = miniMessage;
    }

    @Override
    public String replaceColorCodesWithMiniMessageTags(String message)
    {
        Map<String, String> allReplacements = new HashMap<>();
        COLOR_CODES_MINI_MESSAGE_TAGS.forEach((colorCode, tag) -> COLOR_CODES_PREFIXES.forEach(prefix -> {
            allReplacements.put(prefix + colorCode.toLowerCase(), tag);
            allReplacements.put(prefix + colorCode.toUpperCase(), tag);
        }));
        for (Map.Entry<String, String> entry : allReplacements.entrySet())
        {
            message = message.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
        }
        return message;
    }

    @Override
    public String replaceSenderNamePlaceholder(String message, String senderName)
    {
        return message.replaceAll(Pattern.quote(SENDER_NAME_PLACEHOLDER), senderName);
    }

    @Override
    public String replaceArgumentsPlaceholders(String message, List<String> arguments)
    {
        for (String argumentPlaceholder : extractArgumentsPlaceholders(message))
        {
            List<Integer> argumentsAmount = commandParser.parseArgumentsAmount(argumentPlaceholder.substring(ARGUMENTS_PLACEHOLDER_PREFIX.length()));
            String replacement = buildReplacementForArgumentsAmount(argumentsAmount, arguments);
            message = message.replaceAll(Pattern.quote(argumentPlaceholder), replacement);
        }
        return message;
    }

    @Override
    public Component parseMiniMessage(String message)
    {
        return miniMessage.deserialize(message);
    }

    private List<String> extractArgumentsPlaceholders(String message)
    {
        List<String> arguments = new ArrayList<>();
        Pattern pattern = Pattern.compile(ARGUMENTS_PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            arguments.add(matcher.group());
        }

        return arguments.stream().distinct().collect(Collectors.toList());
    }

    private String buildReplacementForArgumentsAmount(List<Integer> argumentsAmounts, List<String> allArguments)
    {
        if (argumentsAmounts.size() == 0)
            return "";

        // All arguments (-1)
        if (argumentsAmounts.size() == 1 && argumentsAmounts.get(0) == SteveCommand.ANY_AMOUNT_OF_ARGS)
            return String.join(" ", allArguments);

        // Specific amount of arguments
        return argumentsAmounts.stream()
                .filter(argNumber -> argNumber > 0 && argNumber <= allArguments.size())
                .map(argNumber -> allArguments.get(argNumber - 1))
                .collect(Collectors.joining(CommandParser.WORDS_SEPARATOR));
    }
}
