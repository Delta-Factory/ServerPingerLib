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

	/**
	 * 将 Minecraft 的 § 颜色代码转换为 HTML 标签
	 * 用于在 Swing GUI 中显示颜色
	 */
	public static String toHtml(String text) {
		if (text == null) return "";

		StringBuilder html = new StringBuilder("<html><body style='font-family:微软雅黑,sans-serif;'>");
		int pos = 0;
		boolean bold = false, italic = false, underline = false, strikethrough = false;
		String currentColor = "white";

		// 推荐的清晰颜色（避免 Windows 上 #FFFF55 发白）
		String[] colors = {
			"#222222", "#0000CC", "#00CC00", "#00CCCC",
			"#CC0000", "#CC00CC", "#CC7700", "#777777",
			"#555555", "#3366FF", "#55FF55", "#00FFFF",
			"#FF5555", "#FF33CC", "#FFBB00", "#FFFFFF"
		};

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '§' && i + 1 < text.length()) {
				// 添加普通文本
				if (pos < i) {
					html.append(escapeHtml(text.substring(pos, i)));
				}

				char code = Character.toLowerCase(text.charAt(i + 1));
				if (code >= '0' && code <= 'f') {
					int idx = code <= '9' ? code - '0' : code - 'a' + 10;
					currentColor = colors[idx];
					html.append(String.format("<font color='%s'>", currentColor));
				} else {
					switch (code) {
						case 'l': // bold
							html.append("<b>");
							bold = true;
							break;
						case 'o': // italic
							html.append("<i>");
							italic = true;
							break;
						case 'n': // underline
							html.append("<u>");
							underline = true;
							break;
						case 'm': // strikethrough
							html.append("<s>");
							strikethrough = true;
							break;
						case 'r': // reset
							if (bold) html.append("</b>");
							if (italic) html.append("</i>");
							if (underline) html.append("</u>");
							if (strikethrough) html.append("</s>");
							html.append("</font>");
							bold = italic = underline = strikethrough = false;
							currentColor = "white";
							break;
					}
				}
				pos = i + 2;
				i++; // 跳过 code
			}
		}

		// 添加最后一段文本
		if (pos < text.length()) {
			html.append(escapeHtml(text.substring(pos)));
		}

		// 闭合标签
		if (bold) html.append("</b>");
		if (italic) html.append("</i>");
		if (underline) html.append("</u>");
		if (strikethrough) html.append("</s>");
		if (!currentColor.equals("white")) html.append("</font>");

		html.append("</body></html>");
		return html.toString();
	}

	private static String escapeHtml(String s) {
		return s.replace("&", "&amp;")
			.replace("<", "<")
			.replace(">", ">")
			.replace("\"", "&quot;")
			.replace("'", "&#x27;");
	}
}
