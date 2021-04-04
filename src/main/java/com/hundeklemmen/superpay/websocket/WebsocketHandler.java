package com.hundeklemmen.superpay.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.WebSocketResponse;
import com.hundeklemmen.superpay.menus.AcceptMenu;
import com.hundeklemmen.superpay.menus.ServerList;
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
        obj.addProperty("version", addon.SuperPayAddonVersion);
        send(obj.toString());

        if(addon.token.length() == 24 && isClosed() == false) {
            JsonObject verification = new JsonObject();
            verification.addProperty("type", "verification");
            verification.addProperty("token", addon.token);
            verification.addProperty("username", addon.getApi().getPlayerUsername());
            verification.addProperty("uuid", addon.getApi().getPlayerUUID().toString());
            verification.addProperty("version", addon.SuperPayAddonVersion);
            send(verification.toString());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        final WebsocketHandler localcache = this;
        addon.verified = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                localcache.reconnect();
            }
        }).start();
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        WebSocketResponse response = new Gson().fromJson(message, WebSocketResponse.class);
        if(response.getType().equalsIgnoreCase("anmodning") && addon.onSuperAwesome == true){
            if(response.getAnmodning().getServer().equalsIgnoreCase("fikocasino")) {
                Minecraft.getMinecraft().displayGuiScreen(new fikocasino(response.getAnmodning(), addon));
                return;
            }
            Minecraft.getMinecraft().displayGuiScreen(new AcceptMenu(response.getAnmodning(), addon));
        } else if(response.getType().equalsIgnoreCase("verification")){
            addon.verified = response.getVerified();
            if(addon.onSuperAwesome == true){
                if(addon.verified == true) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §aDu er nu autoriseret med SuperPay"));
                } else {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §cFejl, kunne ikke autorisere dig med SuperPay!"));
                }
            }
        } else if(response.getType().equalsIgnoreCase("balance")){
            addon.balance = response.getBalance();
        } else if(response.getType().equalsIgnoreCase("message")){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r " + response.getMessage()));
            addon.balance = response.getBalance();
        } else if(response.getType().equalsIgnoreCase("messageraw")){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r " + response.getMessage()));
        } else if(response.getType().equalsIgnoreCase("serverlist")){
            System.out.println("ServerList View");
            System.out.println("Server Amount: " + response.getServerlist().size());
            Minecraft.getMinecraft().displayGuiScreen(new ServerList(response.getServerlist(), addon));
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