package low.citory.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextColorizer {

	private static final Pattern PATTERN = Pattern.compile("§([0-9a-fk-or])", Pattern.CASE_INSENSITIVE);

	public static String convertToAnsi(String string) {
		Matcher matcher = PATTERN.matcher(string+"§r");
		StringBuilder finallyString = new StringBuilder();

		while (matcher.find()) {
			char color = matcher.group(1).charAt(0);
			String colorCode = "V_"+Character.toUpperCase(color);
			String code = Optional.of(MinecraftColors.valueOf(colorCode)).
				map(MinecraftColors::getAnsiCode).orElse("");

			matcher.appendReplacement(finallyString, Matcher.quoteReplacement(code));
		}
		matcher.appendTail(finallyString);
		return finallyString.toString();
	}

	public static String stripFormatting(String text) {
		return text.replaceAll("§[0-9a-fk-or]", "");
	}
}
