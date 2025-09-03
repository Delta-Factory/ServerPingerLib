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
		pinger.setAnsiMotd(MinecraftANSI.toAnsi(JsonToANSI.convert(rawMotd)));
		//Don't know how to convert hex color into ANSI plz finish this.
		pinger.setPlayersOnline(playersOnline);
		pinger.setMaxPlayers(maxPlayers);
		pinger.setServerPing(ping);
		pinger.setOnline(true);
	}
}
