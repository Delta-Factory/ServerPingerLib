package low.citory.util;

public final class TextColorizer {

	private static final String COLOR_CODE_PATTERN = "§[0-9a-fk-or]";

	/**
	 * 移除所有 Minecraft 颜色和格式代码 (§r, §a, §l 等)
	 */
	public static String stripFormatting(String text) {
		return stripColorCodes(text);
	}

	/**
	 * 移除所有 Minecraft 颜色和格式代码
	 * 此方法可在类内部或外部调用
	 */
	public static String stripColorCodes(String text) {
		if (text == null) return null;
		return text.replaceAll(COLOR_CODE_PATTERN, "");
	}

	/**
	 * 将 Minecraft 的 § 颜色代码转换为 ANSI 转义序列，用于终端着色
	 * 使用 MinecraftANSI 工具类
	 */
	public static String convertToAnsi(String string) {
		if (string == null) return null;
		return MinecraftANSI.toAnsi(string);
	}
}
