package me.expdev.serveraibot.http.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.expdev.serveraibot.http.HttpExchangeWrapper;
import me.expdev.serveraibot.http.action.WebhookAction;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple handler for a request coming from API.AI
 */
public class WebhookHandler implements HttpHandler {

    // Methods
    private static final String POST_METHOD = "POST";

    // Actions
    private static WebhookAction defaultAction = null;
    private Map<String, WebhookAction> actions = new HashMap<String, WebhookAction>();

    @Override
    public void handle(HttpExchange exc) throws IOException {
        // By documentation, API.AI only sends POST requests
        if (!exc.getRequestMethod().toUpperCase().equals(POST_METHOD)) return;

        // Parse request to a json object and get the result
        JsonObject request = new JsonParser().parse(new String(IOUtils.readFully(exc.getRequestBody(), Integer.MAX_VALUE, true))).getAsJsonObject();
        JsonObject result = request.getAsJsonObject("result");

        // Wrap HttpExchange
        HttpExchangeWrapper wrapper = new HttpExchangeWrapper(exc);

        // Route to appropriate handler
        WebhookAction action = actions.get(result.get("action").getAsString());
        if (action == null) {
            // Not found, handle default if set
            if (defaultAction != null) defaultAction.handle(wrapper);
            return;
        }

        // Handle action!
        action.handle(wrapper);
    }

    /**
     * Registers a action for webhook handler
     *
     * @param id Id of action
     * @param action Action to register
     */
    public void registerAction(String id, WebhookAction action) {
        actions.put(id, action);
    }

    /**
     * Registers a default action
     *
     * @param action Action to register
     */
    public static void registerDefaultAction(WebhookAction action) {
        defaultAction = action;
    }

}
