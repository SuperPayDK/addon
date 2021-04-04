package com.hundeklemmen.superpay.listeners;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.menus.ServerList;
import net.labymod.api.events.MessageSendEvent;
import net.minecraft.client.Minecraft;

public class commandEvent implements MessageSendEvent {


    private Addon addon;

    public commandEvent(Addon addon) {
        this.addon = addon;
    }
    @Override
    public boolean onSend(String s) {
        if(addon.onSuperAwesome && s.equalsIgnoreCase("/server")){
            System.out.println("Display GUI");
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "serverlist_request");
            this.addon.websocketHandler.send(obj.toString());
            return true;
        }
        return false;
    }
}
