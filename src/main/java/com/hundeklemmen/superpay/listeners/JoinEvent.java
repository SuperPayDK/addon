package com.hundeklemmen.superpay.listeners;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.Utils;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.network.server.LoginServerEvent;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;

public class JoinEvent {


    private Addon addon;

    public JoinEvent(Addon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void accept(LoginServerEvent event) {
        ServerData serverData = event.getServerData();
        this.addon.server = serverData;
        String ip = serverData.getIp().toLowerCase();
        if(Utils.containsIgnoreCase(ip, "superawesome.dk") || Utils.containsIgnoreCase(ip, "superawesomeminecraftserver.com")){
            this.addon.onSuperAwesome = true;
            System.out.println("Verified: " + this.addon.verified);
            if(this.addon.verified == true) {
                addon.getApi().displayMessageInChat("§8[§aSuperPay§8]§r §aDu er nu autoriseret med SuperPay");

                JsonObject obj = new JsonObject();
                obj.addProperty("type", "playerdata");
                obj.addProperty("username", this.addon.getApi().getPlayerUsername());
                obj.addProperty("uuid", addon.getApi().getPlayerUUID().toString());
                obj.addProperty("version", addon.SuperPayAddonVersion);
                this.addon.websocketHandler.send(obj.toString());
            } else {
                addon.getApi().displayMessageInChat("§8[§aSuperPay§8]§r §cFejl, kunne ikke autorisere dig med SuperPay!");
            }
        } else {
            this.addon.onSuperAwesome = false;
        }
        //God disabled by *Console*.
    }
}
