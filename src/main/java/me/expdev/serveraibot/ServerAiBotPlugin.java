package me.expdev.serveraibot;

import com.sun.net.httpserver.HttpServer;
import me.expdev.serveraibot.http.action.DefaultWebhookAction;
import me.expdev.serveraibot.http.action.PlayersOnlineAction;
import me.expdev.serveraibot.http.handler.WebhookHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main class
 */
public class ServerAiBotPlugin extends JavaPlugin {

    private static final String WEBHOOK_ENDPOINT = "/webhook";

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {
        getLogger().info("Attempting to start a HTTP server");
        try {
            startHttpServer();
        } catch (IOException e) {
            getLogger().severe("Could not start server, hitting the off-switch");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getLogger().info("Plugin is OK (200)!");
    }

    @Override
    public void onDisable() {

    }

    /**
     * Starts a HTTP server and creates a context at /webhook
     *
     * @throws IOException If server could not be started
     */
    private void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(25566), 0);
        server.createContext(WEBHOOK_ENDPOINT, makeHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        getLogger().info("Started a server at " + server.getAddress().getHostName() + ":" + server.getAddress().getPort());
    }

    private WebhookHandler makeHandler() {
        // Create a new handler for webhooks with a default action
        WebhookHandler handler = new WebhookHandler(new DefaultWebhookAction());

        // Also register all actions
        handler.registerAction("players.online", new PlayersOnlineAction());
        return handler;
    }

}
