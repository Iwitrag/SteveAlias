package cz.iwitrag.stevealias.command.operation;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import cz.iwitrag.stevealias.command.CommandParser;
import cz.iwitrag.stevealias.command.SteveCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@EverythingIsNonnullByDefault
public class MessageOperation implements CommandOperation
{
    private static final String SENDER_NAME_PLACEHOLDER = "%sender_name%";
    private static final String ARGUMENTS_PLACEHOLDER_PREFIX = "!";
    private static final String ARGUMENTS_PLACEHOLDER_REGEX = "(![0-9]+(-[0-9]+)?)|(!_)";

    private final CommandParser commandParser;
    private final String message;

    public MessageOperation(CommandParser commandParser, String message)
    {
        this.commandParser = commandParser;
        this.message = message;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments)
    {
        String messageToSend = replaceSenderNamePlaceholder(message, sender.getName());
        messageToSend = replaceArgumentsPlaceholders(messageToSend, arguments);
        sender.sendMessage(new ComponentBuilder(messageToSend).create());
    }

    private String replaceSenderNamePlaceholder(String message, String senderName)
    {
        return message.replaceAll(Pattern.quote(SENDER_NAME_PLACEHOLDER), senderName);
    }

    private String replaceArgumentsPlaceholders(String message, List<String> arguments)
    {
        for (String argumentPlaceholder : extractArgumentsPlaceholders(message))
        {
            List<Integer> argumentsAmount = commandParser.parseArgumentsAmount(argumentPlaceholder.substring(ARGUMENTS_PLACEHOLDER_PREFIX.length()));
            String replacement = buildReplacementForArgumentsAmount(argumentsAmount, arguments);
            message = message.replaceAll(Pattern.quote(argumentPlaceholder), replacement);
        }
        return message;
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
