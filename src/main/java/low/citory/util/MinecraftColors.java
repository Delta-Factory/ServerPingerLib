package low.citory.util;

public enum MinecraftColors {
	BLACK      ("\u001B[30m"),   // §0
	DARK_BLUE  ("\u001B[34m"),   // §1
	DARK_GREEN ("\u001B[32m"),   // §2
	DARK_AQUA  ("\u001B[36m"),   // §3
	DARK_RED   ("\u001B[31m"),   // §4
	DARK_PURPLE("\u001B[35m"),   // §5
	GOLD       ("\u001B[33m"),   // §6
	GRAY       ("\u001B[37m"),   // §7
	DARK_GRAY  ("\u001B[90m"),   // §8
	BLUE       ("\u001B[94m"),   // §9
	GREEN      ("\u001B[92m"),   // §a
	AQUA       ("\u001B[96m"),   // §b
	RED        ("\u001B[91m"),   // §c
	LIGHT_PURPLE("\u001B[95m"),  // §d
	YELLOW     ("\u001B[93m"),   // §e
	WHITE      ("\u001B[97m"),   // §f
	RESET      ("\u001B[0m");    // §r

	private final String ansiCode;

	MinecraftColors(String ansiCode) {
		this.ansiCode = ansiCode;
	}

	public String getAnsiCode() {
		return ansiCode;
	}

	@Override
	public String toString() {
		return ansiCode;
	}

	public static MinecraftColors fromChar(char code) {
		return switch (Character.toLowerCase(code)) {
			case '0' -> BLACK;
			case '1' -> DARK_BLUE;
			case '2' -> DARK_GREEN;
			case '3' -> DARK_AQUA;
			case '4' -> DARK_RED;
			case '5' -> DARK_PURPLE;
			case '6' -> GOLD;
			case '7' -> GRAY;
			case '8' -> DARK_GRAY;
			case '9' -> BLUE;
			case 'a' -> GREEN;
			case 'b' -> AQUA;
			case 'c' -> RED;
			case 'd' -> LIGHT_PURPLE;
			case 'e' -> YELLOW;
			case 'f' -> WHITE;
			case 'r' -> RESET;
			default -> null;
		};
	}
}
