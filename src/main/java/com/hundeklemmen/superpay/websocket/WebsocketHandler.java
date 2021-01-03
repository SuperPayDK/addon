package com.hundeklemmen.superpay.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.WebSocketResponse;
import net.minecraft.client.Minecraft;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class WebsocketHandler extends WebSocketClient {

    private Addon addon;
    private URI serverURI;

    public WebsocketHandler(Addon addon, URI serverURI) {
        super(serverURI);
        this.addon = addon;
        this.serverURI = serverURI;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "connectiondata");
        obj.addProperty("username", addon.getApi().getPlayerUsername());
        obj.addProperty("uuid", addon.getApi().getPlayerUUID().toString());
        send(obj.toString());
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        WebSocketResponse response = new Gson().fromJson(message, WebSocketResponse.class);
        System.out.println("Type: " + response.getType());
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}
