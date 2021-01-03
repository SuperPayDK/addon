package com.hundeklemmen.superpay.listeners;

import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.Utils;
import com.hundeklemmen.superpay.menus.AcceptMenu;
import net.labymod.api.events.MessageReceiveEvent;
import net.minecraft.client.Minecraft;

public class onChat implements MessageReceiveEvent {

    private Addon addon;

    public onChat(Addon addon){
        this.addon = addon;
    }

    @Override
    public boolean onReceive(String colored, String stripped) {
        if(Utils.containsIgnoreCase(stripped, "hund")){
            Minecraft.getMinecraft().displayGuiScreen(new AcceptMenu());
           // Minecraft.getMinecraft().thePlayer.sendChatMessage("ooga booga");
        }
        return false;
    }
}
