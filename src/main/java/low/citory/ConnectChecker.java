package low.citory;

import low.citory.util.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.UUID;

public class ConnectChecker {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectChecker.class);

	private String playerName = "SewerAngel";
	private final int protocol;

	private String serverIP;
	private int serverPORT;

	private String disconnectReason;

	public ConnectChecker(int protocol) {
		this.protocol = protocol;
	}

	public ConnectChecker(int protocol, String playerName) {
		this.protocol = protocol;
		this.playerName = playerName;
	}

	public void setHost(String host, int port) {
		this.serverIP = host;
		this.serverPORT = port;
	}

	public String getDisconnectReason() {
		return this.disconnectReason;
	}

	public boolean connect() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(serverIP, serverPORT), 5000);
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

			ByteArrayOutputStream rawHandshake = new ByteArrayOutputStream();
			DataOutputStream handshake = new DataOutputStream(rawHandshake);

			// Handshake
			handshake.writeByte(0x00);
			PacketUtil.writeVarInt(handshake, protocol);
			PacketUtil.writeString(handshake, serverIP);
			handshake.writeShort(serverPORT);
			PacketUtil.writeVarInt(handshake, 2);

			PacketUtil.writeVarInt(outputStream, rawHandshake.size());
			outputStream.write(rawHandshake.toByteArray());

			// Login Start
			ByteArrayOutputStream rawLoginStart = new ByteArrayOutputStream();
			DataOutputStream loginStart = new DataOutputStream(rawLoginStart);

			loginStart.writeByte(0x00);
			PacketUtil.writeString(loginStart, playerName);

			UUID uuid = UUID.randomUUID();
			PacketUtil.writeUuid(loginStart, uuid);

			PacketUtil.writeVarInt(outputStream, rawLoginStart.size());
			outputStream.write(rawLoginStart.toByteArray());
			outputStream.flush();

			int packetID = PacketUtil.readVarInt(inputStream);
			if (packetID != 0x00) this.disconnectReason = null;
			else this.disconnectReason = PacketUtil.readString(inputStream);
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed to join server!", e);
			return false;
		}
	}
}
