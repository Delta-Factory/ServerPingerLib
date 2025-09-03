package low.citory.util;

enum MinecraftCodes {
	V_0("\u001B[30m"), // BLACK
	V_1("\u001B[34m"), // DARK BLUE
	V_2("\u001B[32m"), // DARK GREEN
	V_3("\u001B[36m"), // DARK AQUA
	V_4("\u001B[31m"), // DARK RED
	V_5("\u001B[35m"), // DARK PURPLE
	V_6("\u001B[33m"), // GOLD
	V_7("\u001B[37m"), // GRAY
	V_8("\u001B[90m"), // DARK GRAY
	V_9("\u001B[94m"), // BLUE
	V_A("\u001B[92m"), // GREEN
	V_B("\u001B[96m"), // AQUA
	V_C("\u001B[91m"), // RED
	V_D("\u001B[95m"), // PURPLE
	V_E("\u001B[93m"), // YELLOW
	V_F("\u001B[97m"), // WHITE
	V_L("\u001B[1m"),  // BOLD
	V_O("\u001B[3m"),  // ITALIC
	V_N("\u001B[4m"),  // UNDERLINE
	V_M("\u001B[9m"),  // STRIKETHROUGH
	V_R("\u001B[0m");  // RESET

	private final String ansiCode;

	MinecraftCodes(String ansiCode) {
		this.ansiCode = ansiCode;
	}

	public String getAnsiCode() {
		return ansiCode;
	}

	@Override
	public String toString() {
		return ansiCode;
	}
}
