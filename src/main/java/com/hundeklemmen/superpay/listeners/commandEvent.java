package com.hundeklemmen.superpay.listeners;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.chat.MessageSendEvent;

public class commandEvent {

    private Addon addon;
    public commandEvent(Addon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onSend(MessageSendEvent event) {
        if(addon.onSuperAwesome && event.getMessage().equalsIgnoreCase("/server")) {
            System.out.println("Display GUI");
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "serverlist_request");
            this.addon.websocketHandler.send(obj.toString());
            event.setCancelled(true);
        }
    }
}
