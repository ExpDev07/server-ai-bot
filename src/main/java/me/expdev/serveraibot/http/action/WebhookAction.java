package me.expdev.serveraibot.http.action;

import me.expdev.serveraibot.http.HttpExchangeWrapper;

/**
 * A simple action for the webhook endpoint
 */
public abstract class WebhookAction {

    public abstract void handle(HttpExchangeWrapper wrapper);

}
