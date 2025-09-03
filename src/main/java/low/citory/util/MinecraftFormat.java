package low.citory.util;

public enum MinecraftFormat {
	BOLD          ("\u001B[1m"),     // §l
	ITALIC        ("\u001B[3m"),     // §o
	UNDERLINE     ("\u001B[4m"),     // §n
	STRIKETHROUGH ("\u001B[9m"),     // §m
	RESET         ("\u001B[0m");     // §r

	private final String ansiCode;

	MinecraftFormat(String ansiCode) {
		this.ansiCode = ansiCode;
	}

	public String getAnsiCode() {
		return ansiCode;
	}

	public static MinecraftFormat fromChar(char code) {
		return switch (Character.toLowerCase(code)) {
			case 'l' -> BOLD;
			case 'o' -> ITALIC;
			case 'n' -> UNDERLINE;
			case 'm' -> STRIKETHROUGH;
			case 'r' -> RESET;
			default -> null;
		};
	}
}
