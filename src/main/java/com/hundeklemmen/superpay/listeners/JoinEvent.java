package com.hundeklemmen.superpay.listeners;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.Utils;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JoinEvent implements Consumer<ServerData> {


    private Addon addon;

    public JoinEvent(Addon addon) {
        this.addon = addon;
    }

    @Override
    public void accept(ServerData serverData) {
        this.addon.server = serverData;
        String ip = serverData.getIp().toLowerCase();
        if(Utils.containsIgnoreCase(ip, "superawesome.dk") || Utils.containsIgnoreCase(ip, "superawesomeminecraftserver.com")){
            this.addon.onSuperAwesome = true;
            if(this.addon.verified == true) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §aDu er nu autoriseret med SuperPay"));

                JsonObject obj = new JsonObject();
                obj.addProperty("type", "playerdata");
                obj.addProperty("username", this.addon.getApi().getPlayerUsername());
                obj.addProperty("uuid", addon.getApi().getPlayerUUID().toString());
                obj.addProperty("version", addon.SuperPayAddonVersion);
                this.addon.websocketHandler.send(obj.toString());
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §cFejl, kunne ikke autorisere dig med SuperPay!"));
            }
        } else {
            this.addon.onSuperAwesome = false;
        }
        //God disabled by *Console*.
    }
}
