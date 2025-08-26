<img src="https://raw.githubusercontent.com/Delta-Factory/.github/refs/heads/main/profile/img/Project_Void.png" alt="Project~Void background">

<H1 align="center">-== LowCitory ==-</H1>

<p align="center" style="font-size: 15px">
    <b>
   		By Lmao Stupid Cion
    </b>
</p>

---

<H3 align="center">-==[ Information ]==-</H3>

<p align="center" style="font-size: 15px">
    <b>
		It`s library for server scanners on Java!
	</b>
</p>

<H3 align="center">-==[ Pinger (lib inside) example usage]==-</H3>

<p align="center" style="font-size: 15px">
	<a href="src/main/java/low/citory/ExampleUsage.java">ExampleUsage.java</a>
</p>

```java
private static final Logger LOGGER = LoggerFactory.getLogger(ExampleUsage.class);

private static final String serverIP = "saturn.minecraft-hosting.net";
private static final int serverPORT = 25398;

public static void main(String[] args) {
	MinecraftPinger server = new MinecraftPinger(serverIP, serverPORT);
	if (!server.isOnline()) LOGGER.info("Server {}:{} is offline", serverIP, serverPORT);
	if (!server.isOnline()) return;
	else {
		LOGGER.info("Server protocol: {}", server.getProtocolVersion());
		LOGGER.info("Server version: {}", server.getVersion());
		LOGGER.info("Server MOTD: {}", server.getAnsiMotd());
		LOGGER.info("Players online: {}/{}", server.getPlayersOnline(), server.getMaxPlayers());
		LOGGER.info("Server ping: {}ms", server.getServerPing());
	}

	ConnectChecker connectChecker = new ConnectChecker(server.getProtocolVersion());
	connectChecker.setHost(serverIP, serverPORT);
	if (connectChecker.connect())
		LOGGER.info("Disconnect reason: {}", connectChecker.getDisconnectReason());
}
```

---
<H1 align="center">Socials</H1>

<p align="center">
  <a href="https://discord.gg/MEBkvJbe4P" target="_blank">
    <img alt="My Server" src="https://img.shields.io/badge/P._Violette-white?style=for-the-badge&logo=discord&logoColor=white&logoSize=64&label=%20&labelColor=5c32a8&color=242323&link=https%3A%2F%2Fdiscord.gg%2FMEBkvJbe4P"></a>
  <a href="https://boosty.to/nionim" target="_blank">
    <img alt="My Boosty" src="https://img.shields.io/badge/DeltaCion-white?style=for-the-badge&logo=boosty&logoColor=white&logoSize=64&label=%20&labelColor=ed7315&color=242323&link=https%3A%2F%2Fboosty.to%2Fnionim"></a>
  <a href="https://t.me/projectviolette" target="_blank">
    <img alt="My Telegram" src="https://img.shields.io/badge/P._Violette-white?style=for-the-badge&logo=telegram&logoColor=white&logoSize=64&label=%20&labelColor=00aeff&color=242323&link=https%3A%2F%2Ft.me%2Fprojectviolette"></a>
</p>
