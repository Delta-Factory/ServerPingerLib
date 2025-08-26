package low.citory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExampleUsage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleUsage.class);

	private static final String testIP = "mc.hypixel.net";
	private static final int testPORT = 25565;

	public static void main(String[] args) {
		String serverIP;
		int serverPORT;

		if (args.length == 2) serverIP = args[0];
			else serverIP = testIP;
		if (args.length == 2) serverPORT = Integer.parseInt(args[1]);
			else serverPORT = testPORT;

		MinecraftPinger server = new MinecraftPinger(serverIP, serverPORT);
		if (!server.isOnline()) LOGGER.info("Server {}:{} is offline", serverIP, serverPORT);
		if (!server.isOnline()) return;
		else {
			LOGGER.info("Server protocol: {}", server.getProtocolVersion());
			LOGGER.info("Server version: {}", server.getVersion());
			// Colors work only with "System.out.println()"
			// Idk how to fix colors with log4j
			LOGGER.info("Server MOTD: {}", server.getAnsiMotd());
			LOGGER.info("Players online: {}/{}", server.getPlayersOnline(), server.getMaxPlayers());
			LOGGER.info("Server ping: {}ms", server.getServerPing());
		}

		ConnectChecker connectChecker = new ConnectChecker(server.getProtocolVersion());
		connectChecker.setHost(serverIP, serverPORT);
		if (connectChecker.connect())
			LOGGER.info("Disconnect reason: {}", connectChecker.getDisconnectReason());
	}
}
