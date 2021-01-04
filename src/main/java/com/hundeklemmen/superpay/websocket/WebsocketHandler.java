package com.hundeklemmen.superpay.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.WebSocketResponse;
import net.minecraft.client.Minecraft;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class WebsocketHandler {

    private Addon addon;
    private URI serverURI;
    private Socket socket;

    public WebsocketHandler(Addon addon) {
        this.addon = addon;
    }

    private void connect(){
        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};
            opts.query = "authorization=" + addon.token;
            if (addon.session != null) {
                opts.query = "authorization=" + addon.token + "&session=" + addon.session;
            }
            socket = IO.socket(addon.websocketURL, opts);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Bukkit.getLogger().info("Connected to SuperPay Addon API");
                }

            }).on("session", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    addon.session = args[0].toString();
                }

            }).on("cmd", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(SuperPay.instance, new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].toString());
                        }
                    }, 0);
                }

            }).on("checkpayment", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    long now = new Date().getTime();
                    SuperPay.lastCheck = now;
                    Bukkit.getScheduler().runTaskAsynchronously(SuperPay.instance, new Runnable() {
                        @Override
                        public void run() {
                            String svar = Utils.get("https://superpayapi.hundeklemmen.com/queue/" + authorization);
                            if(svar.isEmpty()) return;
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<betaling>>(){}.getType();
                            List<betaling> betalinger = gson.fromJson(svar, listType);

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SuperPay.instance, new Runnable() {
                                @Override
                                public void run() {
                                    for (betaling betal : betalinger) {
                                        Bukkit.getServer().getPluginManager().callEvent(
                                                new betalingEvent(
                                                        Bukkit.getOfflinePlayer(
                                                                UUID.fromString(betal.getUuid())
                                                        ),
                                                        betal.getPakke(),
                                                        betal.getAmount(),
                                                        betal.get_id()
                                                )
                                        );
                                    }
                                }
                            });
                        }
                    });
                }
            }).on("stop", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Bukkit.getServer().shutdown();
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Bukkit.getLogger().warning("Disconnected from SuperPay");
                }

            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            this.connect();
        }
    }
}
