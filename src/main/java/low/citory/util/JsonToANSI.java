package low.citory.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonToANSI {

	public static String convert(String rawMotd) {

		try {
			JsonElement root = JsonParser.parseString(rawMotd);
			StringBuilder result = new StringBuilder();
			parseElement(root, result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "A Minecraft Server";
		}
	}

	private static void parseElement(JsonElement element, StringBuilder sb) {
		if (element.isJsonObject()) {
			JsonObject obj = element.getAsJsonObject();

			// 添加 text 字段
			if (obj.has("text")) {
				String text = obj.get("text").getAsString();
				sb.append(text);
			}

			// 递归处理 extra 数组
			if (obj.has("extra")) {
				JsonArray extra = obj.getAsJsonArray("extra");
				for (JsonElement extraElement : extra) {
					parseElement(extraElement, sb);  // 递归处理每个 extra 元素
				}
			}

		} else if (element.isJsonArray()) {
			// 处理数组（如顶层是数组）
			for (JsonElement item : element.getAsJsonArray()) {
				parseElement(item, sb);
			}
		} else if (element.isJsonPrimitive()) {
			// 处理原始类型：字符串
			String value = element.getAsString();
			if ("\n".equals(value)) {
				sb.append("\n");
			} else {
				sb.append(value);
			}
		}
		// 忽略 null 或其他类型
	}
}
