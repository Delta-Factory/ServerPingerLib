package low.citory.util;

import low.citory.MinecraftPinger;

public abstract class AbstractPinger {
	protected static void setServerData(
		MinecraftPinger pinger, String version, int protocol, String rawMotd,
		int playersOnline, int maxPlayers, long ping
	) {
		pinger.setVersion(version);
		pinger.setProtocol(protocol);
		pinger.setRawMotd(rawMotd);
		pinger.setStrippedMotd(TextColorizer.stripFormatting(rawMotd));
		pinger.setAnsiMotd(TextColorizer.convertToAnsi(rawMotd));
		pinger.setPlayersOnline(playersOnline);
		pinger.setMaxPlayers(maxPlayers);
		pinger.setServerPing(ping);
		pinger.setOnline(true);
	}
}
