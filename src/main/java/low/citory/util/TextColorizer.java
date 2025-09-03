package low.citory.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextColorizer {

	private static final Pattern PATTERN = Pattern.compile("§([0-9a-fk-or])", Pattern.CASE_INSENSITIVE);

	public static String convertToAnsi(String string) {
		if (string == null) return null;
		return ansiConverter(string);
	}

	public static String stripFormatting(String string) {
		if (string == null) return null;
		return string.replaceAll("§[0-9a-fk-or]", "");
	}

	private static String ansiConverter(String string) {
		Matcher matcher = PATTERN.matcher(string+"§r");
		StringBuilder finallyString = new StringBuilder();

		while (matcher.find()) {
			char color = matcher.group(1).charAt(0);
			String colorCode = "V_"+Character.toUpperCase(color);
			String code = Optional.of(MinecraftCodes.valueOf(colorCode)).
				map(MinecraftCodes::getAnsiCode).orElse("");

			matcher.appendReplacement(finallyString, Matcher.quoteReplacement(code));
		}
		matcher.appendTail(finallyString);
		return finallyString.toString();
	}
}
