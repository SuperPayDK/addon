package com.hundeklemmen.superpay.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.WebSocketResponse;
import com.hundeklemmen.superpay.menus.AcceptMenu;
import com.hundeklemmen.superpay.menus.partners.fikocasino;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class WebsocketHandler extends WebSocketClient {

    private Addon addon;
    private URI serverURI;
    private String rawURI;

    public WebsocketHandler(Addon addon, String serverURI) throws URISyntaxException {
        super(new URI(serverURI));
        this.addon = addon;
        this.rawURI = serverURI;
        this.serverURI = new URI(serverURI);
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "playerdata");
        obj.addProperty("username", addon.getApi().getPlayerUsername());
        obj.addProperty("uuid", addon.getApi().getPlayerUUID().toString());
        send(obj.toString());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        try {
            addon.websocketHandler = new WebsocketHandler(addon, this.rawURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        WebSocketResponse response = new Gson().fromJson(message, WebSocketResponse.class);
        if(response.getType().equalsIgnoreCase("anmodning") && addon.onSuperAwesome == true){
            if(response.getAnmodning().getServer().equalsIgnoreCase("fikocasino")) {
                Minecraft.getMinecraft().displayGuiScreen(new fikocasino(response.getAnmodning(), addon));
                return;
            }
            Minecraft.getMinecraft().displayGuiScreen(new AcceptMenu(response.getAnmodning(), addon));
        } else if(response.getType().equalsIgnoreCase("verification")){
            addon.verified = response.getVerified();
        } else if(response.getType().equalsIgnoreCase("balance")){
            addon.balance = response.getBalance();
        } else if(response.getType().equalsIgnoreCase("message")){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r " + response.getMessage()));
            addon.balance = response.getBalance();
        }
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