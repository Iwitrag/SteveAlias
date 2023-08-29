package cz.iwitrag.stevealias.text;

import cz.iwitrag.stevealias.annotations.EverythingIsNonnullByDefault;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;

@EverythingIsNonnullByDefault
public interface PlaceholderHandler
{
    String SENDER_NAME_PLACEHOLDER = "%sender_name%";
    String ARGUMENTS_PLACEHOLDER_PREFIX = "!";
    String ARGUMENTS_PLACEHOLDER_REGEX = "(![0-9]+(-[0-9]+)?)|(!_)";
    List<String> COLOR_CODES_PREFIXES = List.of("&", "§");
    Map<String, String> COLOR_CODES_MINI_MESSAGE_TAGS = Map.ofEntries(
            Map.entry("0", "<black>"),
            Map.entry("1", "<dark_blue>"),
            Map.entry("2", "<dark_green>"),
            Map.entry("3", "<dark_aqua>"),
            Map.entry("4", "<dark_red>"),
            Map.entry("5", "<dark_purple>"),
            Map.entry("6", "<gold>"),
            Map.entry("7", "<gray>"),
            Map.entry("8", "<dark_gray>"),
            Map.entry("9", "<blue>"),
            Map.entry("a", "<green>"),
            Map.entry("b", "<aqua>"),
            Map.entry("c", "<red>"),
            Map.entry("d", "<light_purple>"),
            Map.entry("e", "<yellow>"),
            Map.entry("f", "<white>"),
            Map.entry("k", "<obfuscated>"),
            Map.entry("l", "<bold>"),
            Map.entry("m", "<strikethrough>"),
            Map.entry("n", "<underlined>"),
            Map.entry("o", "<italic>"),
            Map.entry("r", "<reset>")
    );

    String replaceColorCodesWithMiniMessageTags(String message);

    String replaceSenderNamePlaceholder(String message, String senderName);

    String replaceArgumentsPlaceholders(String message, List<String> arguments);

    Component parseMiniMessage(String message);
}
