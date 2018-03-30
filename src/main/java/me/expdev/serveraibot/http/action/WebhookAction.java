package me.expdev.serveraibot.http.action;

import me.expdev.serveraibot.http.HttpExchangeWrapper;

/**
 * A simple action for the webhook endpoint
 */
public interface WebhookAction {

    void handle(HttpExchangeWrapper wrapper);

}
