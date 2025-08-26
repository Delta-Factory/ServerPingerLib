package low.citory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleUsage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleUsage.class);

	private static final String serverIP = "saturn.minecraft-hosting.net";
	private static final int serverPORT = 25398;

	public static void main(String[] args) {
		MinecraftPinger server = new MinecraftPinger(serverIP, serverPORT);
		if (!server.isOnline()) LOGGER.info("Server {}:{} is offline", serverIP, serverPORT);
		else {
			LOGGER.info("Server protocol: {}", server.getProtocolVersion());
			LOGGER.info("Server version: {}", server.getVersion());
			LOGGER.info("Server MOTD: {}", server.getAnsiMotd());
			LOGGER.info("Players online: {}/{}", server.getPlayersOnline(), server.getMaxPlayers());
			LOGGER.info("Server ping: {}ms", server.getServerPing());
		}

		ConnectChecker connectChecker = new ConnectChecker(server.getProtocolVersion());
		connectChecker.setHost(serverIP, serverPORT);
		if (connectChecker.connect()) LOGGER.info("Disconnect reason: {}", connectChecker.getDisconnectReason());
	}
}
