package low.citory.versions;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import low.citory.MinecraftPinger;
import low.citory.util.AbstractPinger;
import low.citory.util.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public final class ModernPing extends AbstractPinger {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModernPing.class);

	public static boolean pingServer(MinecraftPinger pinger, String serverIP, int serverPORT) {
		try (Socket socket = new Socket()) {
			List<Object> dat = PacketUtil.getDatIO(socket, serverIP, serverPORT);

			long latency = (long) dat.get(0);
			DataInputStream inputStream = (DataInputStream) dat.get(1);
			DataOutputStream outputStream = (DataOutputStream) dat.get(2);

			ByteArrayOutputStream rawHandshake = new ByteArrayOutputStream();
			DataOutputStream handshake = new DataOutputStream(rawHandshake);

			handshake.writeByte(0x00);
			PacketUtil.writeVarInt(handshake, 765);
			PacketUtil.writeString(handshake, serverIP);
			handshake.writeShort(serverPORT);
			PacketUtil.writeVarInt(handshake, 1);

			PacketUtil.writeVarInt(outputStream, rawHandshake.size());
			outputStream.write(rawHandshake.toByteArray());

			ByteArrayOutputStream statusReq = new ByteArrayOutputStream();
			DataOutputStream tmp = new DataOutputStream(statusReq);
			PacketUtil.writeVarInt(tmp, 0x00);
			byte[] reqBytes = statusReq.toByteArray();

			PacketUtil.writeVarInt(outputStream, reqBytes.length);
			outputStream.write(reqBytes);
			outputStream.flush();

			PacketUtil.readVarInt(inputStream);
			outputStream.flush();

			int packetId = PacketUtil.readVarInt(inputStream);
			if (packetId != 0x00) return false;

			String data = PacketUtil.readString(inputStream);
			parseJsonResponse(pinger, data, latency);

			return true;
		} catch (IOException e) {
			LOGGER.error("Server ping error: ", e);
			return false;
		}
	}

	private static void parseJsonResponse(MinecraftPinger pinger, String data, long ping) {
		try {
			Gson gson = new Gson();
			JsonObject json = gson.fromJson(data, JsonObject.class);

			JsonObject versionData = json.getAsJsonObject("version");
			String version = versionData.get("name").getAsString();
			int protocol = versionData.get("protocol").getAsInt();

			JsonObject playersData = json.getAsJsonObject("players");
			int playersOnline = playersData.get("online").getAsInt();
			int maxPlayers = playersData.get("max").getAsInt();

			JsonElement description = json.get("description");
			String motd = (description.isJsonObject()) ? description.toString() : description.getAsString();

			setServerData(pinger, version, protocol, motd, playersOnline, maxPlayers, ping);
		} catch (Exception e) {
			LOGGER.error("Json parse error: ", e);
		}
	}
}
