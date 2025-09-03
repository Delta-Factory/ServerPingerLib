package low.citory.versions;

import low.citory.MinecraftPinger;
import low.citory.util.AbstractPinger;
import low.citory.util.PacketUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class LegacyPing extends AbstractPinger {
	public static boolean pingServer(MinecraftPinger pinger, String serverIP, int serverPORT) {
		try (Socket socket = new Socket()) {
			List<Object> dat = PacketUtil.getDatIO(socket, serverIP, serverPORT);

			long latency = (long) dat.get(0);
			DataInputStream inputStream = (DataInputStream) dat.get(1);
			DataOutputStream outputStream = (DataOutputStream) dat.get(2);

			outputStream.writeShort(0xFE01);
			outputStream.flush();

			if (inputStream.readUnsignedByte() != 0xFF) return false;

			int dataLength = inputStream.readUnsignedShort();
			byte[] responseData = new byte[dataLength * 2];
			inputStream.readFully(responseData);

			String data = new String(responseData, StandardCharsets.UTF_16BE);
			String[] fields = data.split("\u0000");
			if (fields.length < 6) return false;

			String serverVersion = fields[2];
			String serverMOTD = fields[3];
			int playersOnline = Integer.parseInt(fields[4]);
			int maxPlayers = Integer.parseInt(fields[5]);

			setServerData(pinger, serverVersion, -1, serverMOTD, playersOnline, maxPlayers, latency);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
