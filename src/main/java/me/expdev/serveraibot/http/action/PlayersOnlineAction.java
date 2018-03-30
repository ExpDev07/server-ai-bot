package me.expdev.serveraibot.http.action;

import me.expdev.serveraibot.http.HttpExchangeWrapper;
import org.bukkit.Bukkit;

/**
 * An action to respond with how many players are online
 */
public class PlayersOnlineAction implements WebhookAction {

    @Override
    public void handle(HttpExchangeWrapper wrapper) {
        wrapper.respond("There are " + Bukkit.getOnlinePlayers().size() + " players online");
    }

}
