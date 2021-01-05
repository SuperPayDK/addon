package com.hundeklemmen.superpay.listeners;

import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.Utils;
import net.labymod.api.events.MessageReceiveEvent;

public class onChat implements MessageReceiveEvent {

    private Addon addon;

    public onChat(Addon addon){
        this.addon = addon;
    }

    @Override
    public boolean onReceive(String colored, String stripped) {
        if(Utils.containsIgnoreCase(stripped, "hundree")){
           // Minecraft.getMinecraft().displayGuiScreen(new AcceptMenu(response.getAnmodning()));
           // Minecraft.getMinecraft().thePlayer.sendChatMessage("ooga booga");
        }
        return false;
    }
}
