package low.citory.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PacketUtil {

	// Writers
	public static void writeVarInt(DataOutputStream outputStream, int value) throws IOException {
		while (true) {
			if ((value & 0xFFFFFF80) == 0) outputStream.writeByte(value);
			if ((value & 0xFFFFFF80) == 0) return;

			outputStream.writeByte(value & 0x7F | 0x80);
			value >>>= 7;
		}
	}

	public static void writeString(DataOutputStream outputStream, String value) throws IOException {
		byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
		writeVarInt(outputStream, bytes.length);
		outputStream.write(bytes);
	}

	public static void writeUuid(DataOutputStream outputStream, UUID uuid) throws IOException {
		outputStream.writeLong(uuid.getMostSignificantBits());
		outputStream.writeLong(uuid.getLeastSignificantBits());
	}

	// Readers
	public static int readVarInt(DataInputStream inputStream) throws IOException {
		byte currentByte;
		int length = 0;
		int value = 0;

		do {
			currentByte = inputStream.readByte();
			value |= (currentByte & 0x7F) << (length * 7);
			length++;

			if (length > 5) throw new RuntimeException("VarInt is very big!");
		} while ((currentByte & 0x80) == 0x80);
		return value;
	}

	public static String readString(DataInputStream inputStream) throws IOException {
		int length = readVarInt(inputStream);
		byte[] bytes = new byte[length];
		inputStream.readFully(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	// Only for code optimize
	public static List<Object> getDatIO(Socket socket, String serverIP, int serverPORT) throws IOException {
		long currentTime = System.currentTimeMillis();
		socket.connect(new InetSocketAddress(serverIP, serverPORT), 5000);
		long ping = System.currentTimeMillis() - currentTime;

		DataInputStream inputStream = new DataInputStream(socket.getInputStream());
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		return new ArrayList<>(List.of(ping, inputStream, outputStream));
	}
}
