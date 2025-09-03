package low.citory.util;

public class MinecraftANSI {

	/**
	 * 将 Minecraft 的 § 颜色代码转换为 ANSI 转义序列
	 * 用于在支持 ANSI 的终端中显示颜色
	 */
	public static String toAnsi(String text) {
		if (text == null) return null;

		StringBuilder result = new StringBuilder();
		boolean inColor = false;
		boolean inFormat = false;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '§' && i + 1 < text.length()) {
				char code = text.charAt(i + 1);
				MinecraftColors color = MinecraftColors.fromChar(code);
				if (color != null) {
					result.append(color.getAnsiCode());
					inColor = true;
					i++; // 跳过 color code
					continue;
				}

				MinecraftFormat format = MinecraftFormat.fromChar(code);
				if (format != null) {
					result.append(format.getAnsiCode());
					inFormat = true;
					i++; // 跳过 format code
					continue;
				}
			}
			result.append(c);
		}

		// 最后重置所有样式
		if (inColor || inFormat) {
			result.append(MinecraftColors.RESET.getAnsiCode());
		}

		return result.toString();
	}
}
