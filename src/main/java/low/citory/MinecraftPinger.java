package low.citory;

import low.citory.versions.LegacyPing;
import low.citory.versions.ModernPing;

public final class MinecraftPinger {

	private boolean serverStatus;
	private String serverVersion;
	private int protocolVersion;
	private String serverMOTD;
	private String strippedMOTD;
	private String ansiMOTD;
	private int playersOnline;
	private int maxPlayers;
	private long serverPing;

	private final String serverIP;
	private final int serverPORT;

	public MinecraftPinger(String serverIP, int serverPORT) {
		this.serverIP = serverIP;
		this.serverPORT = serverPORT;
		this.getServerData();
	}

	private void getServerData() {
		if (ModernPing.pingServer(this, serverIP, serverPORT)) return;
		if (LegacyPing.pingServer(this, serverIP, serverPORT)) return;
		this.serverStatus = false;
	}

	// Setters
	public void setOnline(boolean status) {
		this.serverStatus = status;
	}

	public void setVersion(String version) {
		this.serverVersion = version;
	}

	public void setProtocol(int protocol) {
		this.protocolVersion = protocol;
	}

	public void setRawMotd(String rawMotd) {
		this.serverMOTD = rawMotd;
	}

	public void setStrippedMotd(String strippedMotd) {
		this.strippedMOTD = strippedMotd;
	}

	public void setAnsiMotd(String ansiMotd) {
		this.ansiMOTD = ansiMotd;
	}

	public void setPlayersOnline(int playersOnline) {
		this.playersOnline = playersOnline;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setServerPing(long ping) {
		this.serverPing = ping;
	}

	// Getters

	/**
	 * Get server online status
	 * @return boolean
	 */
	public boolean isOnline() {
		return this.serverStatus;
	}

	/**
	 * Get server version
	 * @return string
	 */
	public String getVersion() {
		return this.serverVersion;
	}

	/**
	 * Get protocol version
	 * @return int
	 */
	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	/**
	 * Get server motd
	 * @return string
	 */
	public String getMotd() {
		return this.serverMOTD;
	}

	/**
	 * Get stripped motd
	 * @return string
	 */
	public String getStrippedMotd() {
		return this.strippedMOTD;
	}

	/**
	 * Get motd with console formating
	 * @return string
	 */
	public String getAnsiMotd() {
		return this.ansiMOTD;
	}

	/**
	 * Get current players count
	 * @return int
	 */
	public int getPlayersOnline() {
		return this.playersOnline;
	}

	/**
	 * Get max players count
	 * @return int
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Get server ping
	 * @return long
	 */
	public long getServerPing() {
		return this.serverPing;
	}
}
