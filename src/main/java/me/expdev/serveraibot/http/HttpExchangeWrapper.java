package me.expdev.serveraibot.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A wrapper which provides some utility methods to avoid
 * boilerplate code in actions
 */
public class HttpExchangeWrapper {

    private HttpExchange exchange;

    public HttpExchangeWrapper(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public void respond(String response) {
        try {
            // We want to translate response into a json object
            response = makeJson(response);

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a string and puts it into a json object that api.ai will
     * accept
     *
     * @param speech Speech/response to input
     * @return Stringified json
     */
    private static String makeJson(String speech) {
        if (speech == null) return "{}";

        JsonObject object = new JsonObject();
        object.addProperty("speech", speech);
        object.addProperty("displayText", speech);
        object.addProperty("source", "server-ai-bot");
        return new Gson().toJson(object);
    }

}
