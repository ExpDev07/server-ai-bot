package me.expdev.serveraibot.http.action;

import me.expdev.serveraibot.http.HttpExchangeWrapper;

/**
 * Default action to use if not action is found
 */
public class DefaultWebhookAction extends WebhookAction {

    @Override
    public void handle(HttpExchangeWrapper wrapper) {
        wrapper.respond("Action not found.");
    }
}
